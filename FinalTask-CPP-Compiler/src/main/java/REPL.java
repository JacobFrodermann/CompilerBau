import analysis.SemanticAnalyzer;
import analysis.SymbolCollector;
import ast.*;
import execution.Executor;
import java.util.List;
import java.util.Scanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import symbols.SemanticException;
import symbols.Symbol;
import symbols.SymbolTable;

public class REPL {
  private final SymbolTable symbols = new SymbolTable("global");
  private final SemanticAnalyzer analyzer = new SemanticAnalyzer(symbols);
  private final Executor executor = new Executor(symbols);

  public void run() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("C++ REPL (type :q to quit)");

    while (true) {
      System.out.print(">>> ");
      String input = readInput(scanner);
      if (input == null || input.equals(":q")) break;
      if (input.isBlank()) continue;

      try {
        process(input);
      } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
      }
    }
  }

  private String readInput(Scanner scanner) {
    if (!scanner.hasNextLine()) return null;
    StringBuilder sb = new StringBuilder(scanner.nextLine());
    while (unbalanced(sb.toString())) {
      System.out.print("... ");
      if (!scanner.hasNextLine()) break;
      sb.append("\n").append(scanner.nextLine());
    }
    return sb.toString().trim();
  }

  private boolean unbalanced(String s) {
    int b = 0;
    boolean str = false, esc = false;
    for (char c : s.toCharArray()) {
      if (esc) { esc = false; continue; }
      if (c == '\\') { esc = true; continue; }
      if (c == '"') { str = !str; continue; }
      if (!str && c == '{') b++;
      if (!str && c == '}') b--;
    }
    return b > 0;
  }

  private void process(String input) throws SemanticException {
    // Try as declaration
    ProgramNode ast = parse(input);
    if (ast != null && !ast.declarations().isEmpty()) {
      for (Declaration d : ast.declarations()) handleDecl(d);
      return;
    }
    // Try as statement
    ast = parse("void _f(){" + input + "}");
    if (ast != null && !ast.declarations().isEmpty()) {
      var stmts = ((Declaration.FunctionDef) ast.declarations().get(0)).body().statements();
      for (Statement s : stmts) {
        analyzer.analyzeStatement(s);
        executor.executeStatement(s);
      }
      return;
    }
    System.err.println("Parse error");
  }

  private void handleDecl(Declaration d) throws SemanticException {
    if (d instanceof Declaration.FunctionDef f) {
      symbols.define(new Symbol.FunctionSymbol(f.name(), f.returnType(), f.parameters()));
      executor.addDeclarations(List.of(f));
      analyzer.analyzeDeclaration(f);
      System.out.println("function " + f.name());
    } else if (d instanceof Declaration.ClassDef c) {
      var cs = new SymbolCollector().collect(new ProgramNode(List.of(c)));
      for (var e : cs.getAllSymbols().entrySet())
        try { symbols.define(e.getValue()); } catch (SemanticException ignored) {}
      executor.addDeclarations(List.of(c));
      analyzer.analyzeDeclaration(c);
      System.out.println("class " + c.name());
    } else if (d instanceof Declaration.VarDef v) {
      analyzer.analyzeExpression(v.initializer());
      symbols.define(new Symbol.VariableSymbol(v.name(), v.type(), v.isReference()));
      executor.executeStatement(new Statement.VarDef(v.type(), v.isReference(), v.name(), v.initializer()));
      System.out.println(v.type().name() + " " + v.name());
    } else if (d instanceof Declaration.VarDecl v) {
      symbols.define(new Symbol.VariableSymbol(v.name(), v.type(), v.isReference()));
      executor.executeStatement(new Statement.VarDecl(v.type(), v.isReference(), v.name()));
      System.out.println(v.type().name() + " " + v.name());
    }
  }

  private ProgramNode parse(String input) {
    try {
      var lexer = new cppLexer(CharStreams.fromString(input));
      var parser = new cppParser(new CommonTokenStream(lexer));
      lexer.removeErrorListeners();
      parser.removeErrorListeners();
      var tree = parser.start();
      return parser.getNumberOfSyntaxErrors() == 0 ? new ASTBuilder().visitStart(tree) : null;
    } catch (Exception e) { return null; }
  }
}
