import java.util.ArrayList;
import java.util.List;

public class AST {
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

    static class Assign extends Statement {
        String name;
        Expr value;

        Assign(String n, Expr val) {
            name = n;
            value = val;
        }

        @Override
        public String toString(int i) {
            return indent(i) + name + " := " + value.toString() + "\n";
        }
    }

    static class Comparison extends Expr {
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
        StringBuilder bodyBuilder = new StringBuilder(), elseBuilder = new StringBuilder();

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

    static class WhileLoop extends Statement {

        Comparison cond;
        List<Statement> body;

        WhileLoop(Comparison cond, List<Statement> s) {
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

    static class Prog extends Node {
        List<Statement> code;

        Prog(List<Statement> code) {
            this.code = code;
        }

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

    public static class AstVisitor extends Aufgabe31BaseVisitor<Node> {
        private int indentLevel = 0;
        private static final String INDENT = "    "; // 4 Leerzeichen

        private String indent() {
            return INDENT.repeat(indentLevel);
        }

        @Override
        public Node visitProgram(Aufgabe31Parser.ProgramContext ctx) {
            ArrayList<Statement> s = new ArrayList<>();
            for (Aufgabe31Parser.StatementContext stmt : ctx.statement()) {
                s.add((Statement) visit(stmt));
            }
            return new Prog(s);
        }

        @Override
        public Statement visitStatement(Aufgabe31Parser.StatementContext ctx) {
            if (ctx.assign() != null) {
                return visitAssign(ctx.assign());
            } else if (ctx.loop() != null) {
                return (Statement) visit(ctx.loop());
            } else {
                return (Statement) visit(ctx.conditional());
            }
        }

        @Override
        public WhileLoop visitLoop(Aufgabe31Parser.LoopContext ctx) {
            List<Statement> body = new ArrayList<>();
            for (Aufgabe31Parser.StatementContext stmt : ctx.statement()) {
                body.add(visitStatement(stmt));
            }
            return new WhileLoop(visitComparison(ctx.comparison()), body);
        }

        @Override
        public Conditional visitConditional(Aufgabe31Parser.ConditionalContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(indent()).append("if ").append(visitExpr(ctx.expr())).append(" do\n");

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
            return new Conditional(visitComparison(ctx.comparison()));
        }


        @Override
        public Assign visitAssign(Aufgabe31Parser.AssignContext ctx) {
            return new Assign(ctx.ID().getText(), visitExpr(ctx.expr()));
        }

        @Override
        public Conditional visitExpr(Aufgabe31Parser.ExprContext ctx) {

            if (ctx.comparison() != null) {
                return visit(ctx.comparison());
            } else if (ctx.STRING() != null) {
                return ctx.STRING().getText();
            }
            return null;
        }

        @Override
        public Comparison visitComparison(Aufgabe31Parser.ComparisonContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.addition(0)));

            String op = "";
            if (ctx.EQUAL() != null) op = " == ";
            else if (ctx.NEQUAL() != null) op = " != ";
            else if (ctx.LESSTHAN() != null) op = " < ";
            else if (ctx.GREATERTHAN() != null) op = " > ";


            return new Comparison(op, visitExpr(ctx.expresion()), ctx.addition(1));
        }

        @Override
        public Operation visitAddition(Aufgabe31Parser.AdditionContext ctx) {
            StringBuilder sb = new StringBuilder();
            sb.append(visit(ctx.multiplication(0)));

            for (int i = 1; i < ctx.multiplication().size(); i++) {
                String op = "";
                if (ctx.PLUS(i - 1) != null) op = " + ";
                else if (ctx.MINUS(i - 1) != null) op = " - ";

                sb.append(op).append(visit(ctx.multiplication(i)));
            }

            return new Operation();
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
}
