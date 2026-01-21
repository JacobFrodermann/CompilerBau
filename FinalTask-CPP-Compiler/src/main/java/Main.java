import analysis.SemanticAnalyzer;
import analysis.SymbolCollector;
import ast.ASTPrinter;
import ast.ProgramNode;
import execution.Executor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import symbols.SemanticException;
import symbols.Symbol;
import symbols.SymbolTable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static void main() {
        Path positives = Paths.get("./src/test/resources/pos/");
        Path negatives = Paths.get("./src/test/resources/neg/");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(positives)) {
            for (Path entry : stream) {
                System.out.println("Running" + entry.getFileName());
                if (!test(entry)) {
                    IO.println("Error found");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(positives)) {
            for (Path entry : stream) {
                System.out.println("Running" + entry.getFileName());
                if (!test(entry)) {
                    IO.println("Error found");
                } else {
                    IO.println("Test passed");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean test(Path path) {
        try {
            cppLexer lexer = new cppLexer(CharStreams.fromPath(path));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            cppParser parser = new cppParser(tokens);

            ParseTree tree = parser.start();

            // AST bauen
            ASTBuilder builder = new ASTBuilder();
            ProgramNode ast = builder.visitStart((cppParser.StartContext) tree);

            // AST printen
            ASTPrinter printer = new ASTPrinter();
            //System.out.println(printer.print(ast));

            //System.out.println("\n=== SYMBOL COLLECTION ===");

            // Pass 1: Symbole sammeln
            SymbolCollector collector = new SymbolCollector();
            SymbolTable symbols = collector.collect(ast);

            //System.out.println("Collected symbols:");
            //printSymbols(symbols, 1);

            // Pass 2: Semantische Analyse
            SemanticAnalyzer analyzer = new SemanticAnalyzer(symbols);
            analyzer.analyze(ast);
            //System.out.println("Semantic analysis passed");

            // Pass 3: Execution
            IO.println("Execution:");
            Executor executor = new Executor(symbols);
            executor.execute(ast);
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (SemanticException e) {
            System.out.println("Semantic Error: " + e.getMessage());
            return false;
        } catch (Throwable t) {
            System.out.println("other err happend" + t.getMessage());
            return false;
        }
        return  true;
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
                    System.out.println(indentStr + func.signature() + " -> " + func.returnType().name());
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
                        for (var methodEntry : cls.getAllMethods().entrySet()) {
                            List<Symbol.FunctionSymbol> overloads = methodEntry.getValue();
                            for (Symbol.FunctionSymbol m : overloads) {
                                String virt = cls.isVirtual(m.name()) ? "virtual " : "";
                                System.out.println(indentStr + "    " + virt + m.returnType().name() + " " + m.signature());
                            }
                        }
                    }
                }
            }
        }
    }
}
