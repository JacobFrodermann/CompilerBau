import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.sun.source.tree.LiteralTree;
import my.pkg.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Tree;

public class Main {
    static void main(String... args) throws IOException, URISyntaxException {
        IO.println("Hello World!");


        String input = "a := 0 \n if a > 3 do \n   b := 4 \n else    do \n c := 10 \n end";
        // Einlesen über Konsole/Prompt

        //HelloLexer lexer = new HelloLexer(CharStreams.fromString(input));
        //CommonTokenStream tokens = new CommonTokenStream(lexer);
        //HelloParser parser = new HelloParser(tokens);

        Aufgabe31Lexer lexer = new Aufgabe31Lexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Aufgabe31Parser parser = new Aufgabe31Parser(tokens);

        ParseTree tree = parser.program(); // Start-Regel


        IO.println(tree.toStringTree(parser));
        var a = new PrettyPrintVisitor();
        IO.println(a.visit(tree));
/*
    // Einlesen über den Classpath
    IO.readln("enter?> ");
    try (InputStream in = Main.class.getResourceAsStream("/cpp/vars.cpp")) {
      String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      IO.println("\n\n/cpp/vars.cpp");
      IO.println(text);
    }

    // Einlesen über Dateisystem
    IO.readln("enter?> ");
    URL url = Main.class.getResource("/cpp/expr.cpp");
    String txt = Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);
    IO.println("\n\n/cpp/expr.cpp");
    IO.println(txt);*/
    }

    public static class PrettyPrintVisitor extends Aufgabe31BaseVisitor<String> {
        private int indentLevel = 0;
        private static final String INDENT = "    "; // 4 Leerzeichen

        private String indent() {
            return INDENT.repeat(indentLevel);
        }

        @Override
        public String visitProgram(Aufgabe31Parser.ProgramContext ctx) {
            StringBuilder sb = new StringBuilder();
            for (Aufgabe31Parser.StatementContext stmt : ctx.statement()) {
                sb.append(visit(stmt));
            }
            return sb.toString();
        }

        @Override
        public String visitStatement(Aufgabe31Parser.StatementContext ctx) {
            String result = "";
            if (ctx.vardec() != null) {
                result = indent() + visit(ctx.vardec()) + "\n";
            } else if (ctx.assign() != null) {
                result = indent() + visit(ctx.assign()) + "\n";
            } else if (ctx.expr() != null) {
                result = indent() + visit(ctx.expr()) + "\n";
            } else if (ctx.loop() != null) {
                result = visit(ctx.loop());
            } else if (ctx.condition() != null) {
                result = visit(ctx.condition());
            }
            return result;
        }

        @Override
        public String visitLoop(Aufgabe31Parser.LoopContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(indent()).append("while ").append(visit(ctx.expr())).append(" do\n");

            indentLevel++;
            for (Aufgabe31Parser.StatementContext stmt : ctx.statement()) {
                sb.append(visit(stmt));
            }
            indentLevel--;

            sb.append(indent()).append("end\n");
            return sb.toString();
        }

        @Override
        public String visitCondition(Aufgabe31Parser.ConditionContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(indent()).append("if ").append(visit(ctx.expr())).append(" do\n");

            indentLevel++;
            for (int i = 0; i < ctx.statement().size(); i++) {
                Aufgabe31Parser.StatementContext stmt = ctx.statement(i);

                // Prüfe ob wir beim else-Zweig sind
                if (i > 0 && ctx.getText().contains("elsedo")) {
                    // Finde den Index wo else beginnt
                    String fullText = ctx.getText();
                    int elsePos = fullText.indexOf("elsedo");
                    int stmtPos = fullText.indexOf(stmt.getText());

                    if (stmtPos > elsePos && i == ctx.statement().size() / 2) {
                        indentLevel--;
                        sb.append(indent()).append("else do\n");
                        indentLevel++;
                    }
                }

                sb.append(visit(stmt));
            }
            indentLevel--;

            sb.append(indent()).append("end\n");
            return sb.toString();
        }

        @Override
        public String visitVardec(Aufgabe31Parser.VardecContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(ctx.ID().getText());
            if (ctx.expr() != null) {
                sb.append(" := ").append(visit(ctx.expr()));
            }
            return sb.toString();
        }

        @Override
        public String visitAssign(Aufgabe31Parser.AssignContext ctx) {
            return ctx.ID().getText() + " := " + visit(ctx.expr());
        }

        @Override
        public String visitExpr(Aufgabe31Parser.ExprContext ctx) {
            if (ctx.comparison() != null) {
                return visit(ctx.comparison());
            } else if (ctx.STRING() != null) {
                return ctx.STRING().getText();
            }
            return "";
        }

        @Override
        public String visitComparison(Aufgabe31Parser.ComparisonContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.addition(0)));

            for (int i = 1; i < ctx.addition().size(); i++) {
                String op = "";
                if (ctx.EQUAL(i - 1) != null) op = " == ";
                else if (ctx.NEQUAL(i - 1) != null) op = " != ";
                else if (ctx.LESSTHAN(i - 1) != null) op = " < ";
                else if (ctx.GREATERTHAN(i - 1) != null) op = " > ";

                sb.append(op).append(visit(ctx.addition(i)));
            }

            return sb.toString();
        }

        @Override
        public String visitAddition(Aufgabe31Parser.AdditionContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.multiplication(0)));

            for (int i = 1; i < ctx.multiplication().size(); i++) {
                String op = "";
                if (ctx.PLUS(i - 1) != null) op = " + ";
                else if (ctx.MINUS(i - 1) != null) op = " - ";

                sb.append(op).append(visit(ctx.multiplication(i)));
            }

            return sb.toString();
        }

        @Override
        public String visitMultiplication(Aufgabe31Parser.MultiplicationContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.idOrNum(0)));

            for (int i = 1; i < ctx.idOrNum().size(); i++) {
                String op = "";
                if (ctx.MUL(i - 1) != null) op = " * ";
                else if (ctx.DIV(i - 1) != null) op = " / ";

                sb.append(op).append(visit(ctx.idOrNum(i)));
            }

            return sb.toString();
        }

        @Override
        public String visitIdOrNum(Aufgabe31Parser.IdOrNumContext ctx) {
            if (ctx.ID() != null) {
                return ctx.ID().getText();
            } else if (ctx.NUMBER() != null) {
                return ctx.NUMBER().getText();
            } else if (ctx.expr() != null) {
                return "(" + visit(ctx.expr()) + ")";
            }
            return "";
        }
    }

    static abstract class Node {

    }

    abstract static class Expr extends Node {

    }

    abstract static class Statement extends Node {
        public abstract String toString(int i);
    }

    static class NumOrId extends Expr {
        String literal;

        NumOrId(String l) {
            literal = l;
        }

        public String toString() {
            return literal;
        }
    }

    static class Operation extends Expr {
        String operator;
        Expr a, b;

        Operation(String op, Expr c, Expr d) {
            operator = op;
            a = c;
            b = d;
        }

        public String toString() {
            return a.toString() + " " + operator + " " + b.toString();
        }
    }

    static class Assing extends Statement {
        String name;
        Expr value;

        Assing(String n, Expr val) {
            name = n;
            value = val;
        }

        @Override
        public String toString(int i) {
            return indent(i) + name + " := " + value.toString() + "\n";
        }
    }

    static class Comparison {
        String operator;
        Expr a, b;

        Comparison(String op, Expr c, Expr d) {
            operator = op;
            a = c;
            b = d;
        }

        public String toString() {
            return a.toString() + " " + operator + " " + b.toString();
        }
    }

    static class Conditional extends Statement {
        Comparison cond;
        List<Statement> ifBody, elseBody;
        StringBuilder bodyBuilder, elseBuilder;

        Conditional(Comparison comp, List<Statement> ifBody, List<Statement> elseBody) {
            this.cond = comp;
            this.ifBody = ifBody;
            this.elseBody = elseBody;
        }

        public String toString(int i) {
            for (Statement statement : ifBody) {
                bodyBuilder.append(statement.toString(i + 1));
            }
            for (Statement s : elseBody) {
                elseBuilder.append(s.toString(i + 1));
            }
            return indent(i) + "if " + cond.toString() + " do\n" + bodyBuilder.toString() + (elseBody.isEmpty() ? indent(i) + "end\n" : indent(i) + "else do\n" + elseBuilder.toString() + indent(i) + "end\n");
        }

    }

    static class whileLoop extends Statement {

        Comparison cond;
        List<Statement> body;

        whileLoop(Comparison cond, List<Statement> s) {
            this.cond = cond;
            body = s;
        }

        @Override
        public String toString(int i) {
            StringBuilder sb = new StringBuilder();
            for (Statement s : body) {
                sb.append(s.toString(i + 1));
            }
            return indent(i) + "while " + cond.toString() + " do\n" + sb.toString() + indent(i) + "end\n";
        }
    }

    static class Prog {
        List<Statement> code;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Statement s : code) {
                sb.append(s.toString(0));
            }
            return sb.toString();
        }
    }

    public static String indent(int i) {
        return "    ".repeat(i);
    }
}

