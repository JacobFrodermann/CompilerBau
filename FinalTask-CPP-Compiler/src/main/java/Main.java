import ast.ASTPrinter;
import ast.ProgramNode;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Lesen aus file.cpp
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

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }
}
