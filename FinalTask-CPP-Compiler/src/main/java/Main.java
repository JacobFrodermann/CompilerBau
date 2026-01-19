import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  public static void main(String[] args) {
    cppLexer lexer = new cppLexer(CharStreams.fromString("int main() {\nint i = 0;\n}\n"));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    cppParser parser = new cppParser(tokens);

    ParseTree tree = parser.start();

    System.out.println(tree.toStringTree(parser));
  }
}
