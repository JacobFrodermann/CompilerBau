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
                return visitLoop(ctx.loop());
            } else {
                return visitConditional(ctx.conditional());
            }
        }

        @Override
        public WhileLoop visitLoop(Aufgabe31Parser.LoopContext ctx) {
            List<Statement> body = new ArrayList<>();
            for (Aufgabe31Parser.StatementContext sctx : ctx.statement()) {
                body.add((Statement) visit(sctx));
            }
            return new WhileLoop(visitComparison(ctx.comparison()), body);
        }

        @Override
        public Conditional visitConditional(Aufgabe31Parser.ConditionalContext ctx) {
            // else-Grenze über das echte 'else'-Terminal finden
            Integer elseTokenIndex = null;
            if (ctx.children != null) {
                for (org.antlr.v4.runtime.tree.ParseTree ch : ctx.children) {
                    if (ch instanceof org.antlr.v4.runtime.tree.TerminalNode tn) {
                        if ("else".equals(tn.getText())) {
                            elseTokenIndex = tn.getSymbol().getTokenIndex();
                            break;
                        }
                    }
                }
            }

            List<Statement> ifBody = new ArrayList<>();
            List<Statement> elseBody = new ArrayList<>();

            for (Aufgabe31Parser.StatementContext sctx : ctx.statement()) {
                Statement s = (Statement) visit(sctx);
                if (elseTokenIndex == null) {
                    ifBody.add(s);
                } else {
                    int startIdx = sctx.getStart().getTokenIndex();
                    if (startIdx < elseTokenIndex) {
                        ifBody.add(s);
                    } else {
                        elseBody.add(s);
                    }
                }
            }

            return new Conditional(visitComparison(ctx.comparison()), ifBody, elseBody);
        }

        @Override
        public Assign visitAssign(Aufgabe31Parser.AssignContext ctx) {
            Expr value;
            if (ctx.idOrNum() != null) {
                value = visitIdOrNum(ctx.idOrNum());
            } else {
                // STRING
                value = new NumOrId(ctx.STRING().getText());
            }
            return new Assign(ctx.ID().getText(), value);
        }

        @Override
        public Expr visitExpr(Aufgabe31Parser.ExprContext ctx) {
            if (ctx.comparison() != null) {
                return visitComparison(ctx.comparison());
            } else if (ctx.STRING() != null) {
                return new NumOrId(ctx.STRING().getText());
            }
            return null;
        }

        @Override
        public Comparison visitComparison(Aufgabe31Parser.ComparisonContext ctx) {
            String op;
            Expr left;
            Expr right;

            if (ctx.STRING().size() == 2) {
                left = new NumOrId(ctx.STRING(0).getText());
                right = new NumOrId(ctx.STRING(1).getText());
                if (ctx.EQUAL() != null) op = "==";
                else op = "!="; // nur == oder != bei Strings
            } else {
                left = visitAddition(ctx.addition(0));
                right = visitAddition(ctx.addition(1));
                if (ctx.EQUAL() != null) op = "==";
                else if (ctx.NEQUAL() != null) op = "!=";
                else if (ctx.LESSTHAN() != null) op = "<";
                else op = ">"; // GREATERTHAN
            }

            return new Comparison(op, left, right);
        }

        @Override
        public Expr visitAddition(Aufgabe31Parser.AdditionContext ctx) {
            Expr acc = visitMultiplication(ctx.multiplication(0));
            int n = ctx.multiplication().size();
            for (int i = 1; i < n; i++) {
                // Kinder sind: mult (op mult)*  -> Operator steht bei Kind 2*i-1
                String opTxt = ctx.getChild(2 * i - 1).getText();
                Expr right = visitMultiplication(ctx.multiplication(i));
                acc = new Operation(opTxt, acc, right);
            }
            return acc;
        }

        @Override
        public Expr visitMultiplication(Aufgabe31Parser.MultiplicationContext ctx) {
            Expr acc = visitIdOrNum(ctx.idOrNum(0));
            int n = ctx.idOrNum().size();
            for (int i = 1; i < n; i++) {
                String opTxt = ctx.getChild(2 * i - 1).getText(); // "*" oder "/"
                Expr right = visitIdOrNum(ctx.idOrNum(i));
                acc = new Operation(opTxt, acc, right);
            }
            return acc;
        }

        @Override
        public Expr visitIdOrNum(Aufgabe31Parser.IdOrNumContext ctx) {
            if (ctx.ID() != null) {
                return new NumOrId(ctx.ID().getText());
            } else {
                return new NumOrId(ctx.NUMBER().getText());
            }
        }

    }
}

