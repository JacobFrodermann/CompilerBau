import analysis.SemanticAnalyzer;
import analysis.SymbolCollector;
import ast.*;
import execution.Executor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import symbols.SemanticException;
import symbols.SymbolTable;

public class REPL {
  private final SymbolTable globalSymbols;
  private final Executor executor;
  private final List<Declaration> declarations;
  private final BufferedReader reader;

  public REPL() {
    this.globalSymbols = new SymbolTable("global");
    this.executor = new Executor(globalSymbols);
    this.declarations = new ArrayList<>();
    this.reader = new BufferedReader(new InputStreamReader(System.in));
  }

  public void run(String[] args) {
    // Load initial file if provided
    if (args.length > 0) {
      loadFile(args[0]);
    }

    System.out.println("C++ Interpreter REPL");
    System.out.println("Type ':quit' or ':exit' to exit, ':help' for help");
    System.out.println();

    // Main REPL loop
    while (true) {
      System.out.print(">>> ");
      System.out.flush();

      try {
        String input = readInput();
        if (input == null) {
          break; // EOF
        }

        input = input.trim();
        if (input.isEmpty()) {
          continue;
        }

        // Handle special commands
        if (input.startsWith(":")) {
          if (handleCommand(input)) {
            break;
          }
          continue;
        }

        // Process C++ input
        processInput(input);

      } catch (IOException e) {
        System.err.println("Error reading input: " + e.getMessage());
      }
    }

    System.out.println("Goodbye!");
  }

  private void loadFile(String filePath) {
    try {
      Path path = Path.of(filePath);
      if (!Files.exists(path)) {
        System.err.println("File not found: " + filePath);
        return;
      }

      System.out.println("Loading " + filePath + "...");

      cppLexer lexer = new cppLexer(CharStreams.fromPath(path));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      cppParser parser = new cppParser(tokens);

      ParseTree tree = parser.start();

      // Build AST
      ASTBuilder builder = new ASTBuilder();
      ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

      // Collect symbols (multi-pass for define-after-use)
      SymbolCollector collector = new SymbolCollector();
      SymbolTable fileSymbols = collector.collect(ast);

      // Merge symbols into global symbol table
      mergeSymbols(fileSymbols);

      // Store declarations
      declarations.addAll(ast.declarations());

      // Semantic analysis
      SemanticAnalyzer analyzer = new SemanticAnalyzer(globalSymbols);
      analyzer.analyze(ast);

      // Execute main() if present
      Declaration.FunctionDef main = findMain();
      if (main != null) {
        System.out.println("Executing main()...");
        System.out.println();
        executor.executeWithDeclarations(declarations);
      } else {
        System.out.println("No main() function found - entering REPL mode");
      }

      System.out.println();

    } catch (IOException e) {
      System.err.println("Error loading file: " + e.getMessage());
    } catch (SemanticException e) {
      System.err.println("Semantic error: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private void mergeSymbols(SymbolTable source) {
    for (var entry : source.getAllSymbols().entrySet()) {
      try {
        globalSymbols.define(entry.getValue());
      } catch (SemanticException e) {
        // Ignore duplicates from built-ins
      }
    }
  }

  private Declaration.FunctionDef findMain() {
    for (Declaration decl : declarations) {
      if (decl instanceof Declaration.FunctionDef f && f.name().equals("main")) {
        return f;
      }
    }
    return null;
  }

  private String readInput() throws IOException {
    StringBuilder input = new StringBuilder();
    String line = reader.readLine();

    if (line == null) {
      return null;
    }

    input.append(line);

    // Check if we need more input (incomplete statement)
    while (needsMoreInput(input.toString())) {
      System.out.print("... ");
      System.out.flush();
      line = reader.readLine();
      if (line == null) {
        break;
      }
      input.append("\n").append(line);
    }

    return input.toString();
  }

  private boolean needsMoreInput(String input) {
    // Count braces to detect incomplete blocks
    int braces = 0;
    int parens = 0;
    boolean inString = false;
    boolean inChar = false;
    boolean escape = false;

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);

      if (escape) {
        escape = false;
        continue;
      }

      if (c == '\\') {
        escape = true;
        continue;
      }

      if (c == '"' && !inChar) {
        inString = !inString;
        continue;
      }

      if (c == '\'' && !inString) {
        inChar = !inChar;
        continue;
      }

      if (inString || inChar) {
        continue;
      }

      switch (c) {
        case '{' -> braces++;
        case '}' -> braces--;
        case '(' -> parens++;
        case ')' -> parens--;
      }
    }

    // Need more input if braces or parens are unbalanced
    if (braces > 0 || parens > 0) {
      return true;
    }

    // Check for class or function definition without closing brace
    String trimmed = input.trim();
    if (trimmed.startsWith("class ") && !trimmed.endsWith(";")) {
      return true;
    }

    // Check for function definition (type name params) without block
    if (trimmed.endsWith(")") && !trimmed.endsWith(";") && !trimmed.contains("{")) {
      // Could be a function definition header - need the body
      return true;
    }

    return false;
  }

  private boolean handleCommand(String input) {
    String cmd = input.toLowerCase();

    switch (cmd) {
      case ":quit", ":exit", ":q" -> {
        return true;
      }
      case ":help", ":h" -> {
        printHelp();
      }
      case ":symbols", ":sym" -> {
        printSymbols();
      }
      case ":clear", ":cls" -> {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
      default -> System.out.println("Unknown command: " + input);
    }

    return false;
  }

  private void printHelp() {
    System.out.println("REPL Commands:");
    System.out.println("  :quit, :exit, :q  - Exit the REPL");
    System.out.println("  :help, :h         - Show this help");
    System.out.println("  :symbols, :sym    - Show defined symbols");
    System.out.println("  :clear, :cls      - Clear the screen");
    System.out.println();
    System.out.println("You can enter:");
    System.out.println("  - Statements (e.g., int x = 5;)");
    System.out.println("  - Expressions (e.g., x + 3)");
    System.out.println("  - Function definitions");
    System.out.println("  - Class definitions");
  }

  private void printSymbols() {
    System.out.println("Defined symbols:");
    for (var entry : globalSymbols.getAllSymbols().entrySet()) {
      System.out.println("  " + entry.getKey() + ": " + entry.getValue());
    }
  }

  private void processInput(String input) {
    try {
      // Try to parse as a top-level declaration first
      if (tryParseDeclaration(input)) {
        return;
      }

      // Try to parse as a statement
      if (tryParseStatement(input)) {
        return;
      }

      // Try to parse as an expression (and print the result)
      tryParseExpression(input);

    } catch (SemanticException e) {
      System.err.println("Semantic error: " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private boolean tryParseDeclaration(String input) throws SemanticException {
    // Wrap input for parsing as top-level
    cppLexer lexer = new cppLexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    cppParser parser = new cppParser(tokens);

    // Remove default error listeners to suppress noise
    parser.removeErrorListeners();
    lexer.removeErrorListeners();

    ParseTree tree = parser.start();

    if (parser.getNumberOfSyntaxErrors() > 0) {
      return false;
    }

    ASTBuilder builder = new ASTBuilder();
    ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

    if (ast.declarations().isEmpty()) {
      return false;
    }

    // Check if it's a function or class definition
    for (Declaration decl : ast.declarations()) {
      if (decl instanceof Declaration.FunctionDef || decl instanceof Declaration.ClassDef) {
        // Collect and define symbols
        SymbolCollector collector = new SymbolCollector();
        SymbolTable newSymbols = collector.collect(ast);
        mergeSymbols(newSymbols);

        // Add to declarations list
        declarations.addAll(ast.declarations());
        executor.addDeclarations(ast.declarations());

        // Analyze
        SemanticAnalyzer analyzer = new SemanticAnalyzer(globalSymbols);
        analyzer.analyze(ast);

        if (decl instanceof Declaration.FunctionDef f) {
          System.out.println("Defined function: " + f.name());
        } else if (decl instanceof Declaration.ClassDef c) {
          System.out.println("Defined class: " + c.name());
        }
        return true;
      }
    }

    // Handle variable declarations at top level by executing them
    for (Declaration decl : ast.declarations()) {
      if (decl instanceof Declaration.VarDef varDef) {
        // Analyze the initializer first (before adding to symbol table)
        SemanticAnalyzer analyzer = new SemanticAnalyzer(globalSymbols);
        analyzer.analyzeExpression(varDef.initializer());

        // Add to symbol table for future semantic analysis
        symbols.Symbol.VariableSymbol varSymbol =
            new symbols.Symbol.VariableSymbol(varDef.name(), varDef.type(), varDef.isReference());
        globalSymbols.define(varSymbol);

        // Convert to statement and execute
        Statement.VarDef stmt =
            new Statement.VarDef(varDef.type(), varDef.isReference(), varDef.name(), varDef.initializer());
        executor.executeStatement(stmt);
        System.out.println("Defined: " + varDef.type().name() + " " + varDef.name());
        return true;
      } else if (decl instanceof Declaration.VarDecl varDecl) {
        // Add to symbol table for semantic analysis
        symbols.Symbol.VariableSymbol varSymbol =
            new symbols.Symbol.VariableSymbol(varDecl.name(), varDecl.type(), varDecl.isReference());
        globalSymbols.define(varSymbol);

        Statement.VarDecl stmt =
            new Statement.VarDecl(varDecl.type(), varDecl.isReference(), varDecl.name());
        executor.executeStatement(stmt);
        System.out.println("Declared: " + varDecl.type().name() + " " + varDecl.name());
        return true;
      }
    }

    return false;
  }

  private boolean tryParseStatement(String input) {
    try {
      // Wrap in a dummy function to parse as statement
      String wrapped = "void __repl__() { " + input + " }";

      cppLexer lexer = new cppLexer(CharStreams.fromString(wrapped));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      cppParser parser = new cppParser(tokens);

      parser.removeErrorListeners();
      lexer.removeErrorListeners();

      ParseTree tree = parser.start();

      if (parser.getNumberOfSyntaxErrors() > 0) {
        return false;
      }

      ASTBuilder builder = new ASTBuilder();
      ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

      if (ast.declarations().isEmpty()) {
        return false;
      }

      Declaration.FunctionDef wrapper = (Declaration.FunctionDef) ast.declarations().get(0);
      List<Statement> statements = wrapper.body().statements();

      if (statements.isEmpty()) {
        return false;
      }

      // Analyze statements in context
      SemanticAnalyzer analyzer = new SemanticAnalyzer(globalSymbols);
      for (Statement stmt : statements) {
        analyzer.analyzeStatement(stmt);
      }

      // Execute statements
      for (Statement stmt : statements) {
        executor.executeStatement(stmt);
      }

      return true;

    } catch (Exception e) {
      return false;
    }
  }

  private void tryParseExpression(String input) throws SemanticException {
    try {
      // Ensure input ends with semicolon for expression statement
      String exprInput = input.endsWith(";") ? input : input + ";";

      // Wrap in a function that returns the value
      String wrapped = "void __repl__() { " + exprInput + " }";

      cppLexer lexer = new cppLexer(CharStreams.fromString(wrapped));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      cppParser parser = new cppParser(tokens);

      parser.removeErrorListeners();
      lexer.removeErrorListeners();

      ParseTree tree = parser.start();

      if (parser.getNumberOfSyntaxErrors() > 0) {
        System.err.println("Syntax error in input");
        return;
      }

      ASTBuilder builder = new ASTBuilder();
      ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

      if (ast.declarations().isEmpty()) {
        System.err.println("Could not parse input");
        return;
      }

      Declaration.FunctionDef wrapper = (Declaration.FunctionDef) ast.declarations().get(0);
      List<Statement> statements = wrapper.body().statements();

      if (statements.isEmpty()) {
        return;
      }

      // Analyze and execute
      SemanticAnalyzer analyzer = new SemanticAnalyzer(globalSymbols);
      for (Statement stmt : statements) {
        analyzer.analyzeStatement(stmt);
        Object result = executor.executeStatementWithResult(stmt);

        // Print result for expression statements
        if (result != null && isExpressionStatement(stmt)) {
          printValue(result);
        }
      }

    } catch (SemanticException e) {
      throw e;
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private boolean isExpressionStatement(Statement stmt) {
    return stmt instanceof Statement.FunctionCall
        || stmt instanceof Statement.MethodCall
        || stmt instanceof Statement.Assignment;
  }

  private void printValue(Object value) {
    if (value instanceof Boolean b) {
      System.out.println(b ? "true" : "false");
    } else if (value instanceof Integer i) {
      System.out.println(i);
    } else if (value instanceof Character c) {
      System.out.println(c);
    } else if (value instanceof String s) {
      System.out.println(s);
    } else if (value != null) {
      System.out.println(value);
    }
  }

  public static void main(String[] args) {
    new REPL().run(args);
  }
}
