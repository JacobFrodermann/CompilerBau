import analysis.SemanticAnalyzer;
import analysis.SymbolCollector;
import ast.ASTPrinter;
import ast.ProgramNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import symbols.SemanticException;
import symbols.Symbol;
import symbols.SymbolTable;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            cppLexer lexer = new cppLexer(CharStreams.fromPath(Paths.get("./src/test/resources/file.cpp")));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            cppParser parser = new cppParser(tokens);

            ParseTree tree = parser.start();

            // AST bauen
            ASTBuilder builder = new ASTBuilder();
            ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

            // AST printen
            ASTPrinter printer = new ASTPrinter();
            System.out.println(printer.print(ast));

            System.out.println("\n=== SYMBOL COLLECTION ===");

            // Pass 1: Symbole sammeln
            SymbolCollector collector = new SymbolCollector();
            SymbolTable symbols = collector.collect(ast);

            System.out.println("Collected symbols:");
            printSymbols(symbols, 1);

            // Pass 2: Semantische Analyse
            SemanticAnalyzer analyzer = new SemanticAnalyzer(symbols);
            analyzer.analyze(ast);
            System.out.println("Semantic analysis passed");

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (SemanticException e) {
            System.err.println("Semantic Error: " + e.getMessage());
        }
    }


    private static void printSymbols(SymbolTable symbols, int indent) {
        String indentStr = "  ".repeat(indent);

        for (var entry : symbols.getAllSymbols().entrySet()) {
            Symbol sym = entry.getValue();

            switch (sym) {
                case Symbol.VariableSymbol var -> {
                    String ref = var.isReference() ? "&" : "";
                    System.out.println(indentStr + var.name() + ": " + var.type().name() + ref);
                }

                case Symbol.FunctionSymbol func -> {
                    System.out.println(indentStr + func.name() + func.signature().substring(func.name().length()) + " -> " + func.returnType().name());
                }

                case Symbol.ClassSymbol cls -> {
                    String base = cls.baseClassName() != null ? " : " + cls.baseClassName() : "";
                    System.out.println(indentStr + "class " + cls.name() + base);

                    // Felder
                    if (!cls.getAllFields().isEmpty()) {
                        System.out.println(indentStr + "  Fields:");
                        for (var field : cls.getAllFields().entrySet()) {
                            Symbol.VariableSymbol f = field.getValue();
                            System.out.println(indentStr + "    " + f.type().name() + " " + f.name());
                        }
                    }

                    // Methoden
                    if (!cls.getAllMethods().isEmpty()) {
                        System.out.println(indentStr + "  Methods:");
                        for (var method : cls.getAllMethods().entrySet()) {
                            Symbol.FunctionSymbol m = method.getValue();
                            String virt = cls.isVirtual(m.name()) ? "virtual " : "";
                            System.out.println(indentStr + "    " + virt + m.returnType().name() + " " + m.signature());
                        }
                    }
                }
            }
        }
    }

}
