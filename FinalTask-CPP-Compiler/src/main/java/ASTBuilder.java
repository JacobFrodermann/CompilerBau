import ast.*;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends cppBaseVisitor<ASTNode> {

    // Start: toplvl* EOF
    @Override
    public ProgramNode visitStart(cppParser.StartContext ctx) {
        List<Declaration> declarations = new ArrayList<>();
        for (var toplvlCtx : ctx.toplvl()) {
            for (var child : toplvlCtx.children) {
                ASTNode node = visit(child);
                if (node instanceof Declaration decl) {
                    declarations.add(decl);
                }
            }
        }
        return new ProgramNode(declarations);
    }

    // Function Definition
    @Override
    public Declaration.FunctionDef visitFunctionDef(cppParser.FunctionDefContext ctx) {
        Type returnType = new Type(ctx.OBJ_NAME(0).getText());
        String name = ctx.OBJ_NAME(1).getText();
        List<Parameter> params = parseParams(ctx.params());
        Statement.Block body = visitBlock(ctx.block());
        return new Declaration.FunctionDef(returnType, name, params, body);
    }

    // Function Declaration
    @Override
    public Declaration.FunctionDecl visitFunctionDecl(cppParser.FunctionDeclContext ctx) {
        Type returnType = new Type(ctx.OBJ_NAME(0).getText());
        String name = ctx.OBJ_NAME(1).getText();
        List<Parameter> params = parseParams(ctx.params());
        return new Declaration.FunctionDecl(returnType, name, params);
    }

    // Parameters parsen (für Definitionen)
    private List<Parameter> parseParams(cppParser.ParamsContext ctx) {
        List<Parameter> params = new ArrayList<>();
        if (ctx.param() != null) {
            for (var paramCtx : ctx.param()) {
                Type type = new Type(paramCtx.OBJ_NAME(0).getText());
                boolean isRef = paramCtx.getText().contains("&");
                String name = paramCtx.OBJ_NAME(1).getText();
                params.add(new Parameter(type, isRef, name));
            }
        }
        return params;
    }

    // Class Definition
    @Override
    public Declaration.ClassDef visitClassDefinition(cppParser.ClassDefinitionContext ctx) {
        String name = ctx.OBJ_NAME(0).getText();
        String baseClass = null;
        if (ctx.OBJ_NAME().size() > 1) {
            baseClass = ctx.OBJ_NAME(1).getText();
        }

        List<ClassMember.FieldDecl> fields = new ArrayList<>();
        List<ClassMember.ConstructorDef> constructors = new ArrayList<>();
        List<ClassMember.MethodDef> methods = new ArrayList<>();

        var classBodyCtx = ctx.classBody();
        if (classBodyCtx != null) {
            for (var memberCtx : classBodyCtx.children) {
                if (memberCtx instanceof cppParser.FieldDeclContext fieldCtx) {
                    Type type = new Type(fieldCtx.OBJ_NAME(0).getText());
                    String fieldName = fieldCtx.OBJ_NAME(1).getText();
                    fields.add(new ClassMember.FieldDecl(type, fieldName));
                } else if (memberCtx instanceof cppParser.ConstructorDefContext ctorCtx) {
                    String ctorName = ctorCtx.OBJ_NAME().getText();
                    List<Parameter> params = parseParams(ctorCtx.params());
                    Statement.Block body = visitBlock(ctorCtx.block());
                    constructors.add(new ClassMember.ConstructorDef(ctorName, params, body));
                } else if (memberCtx instanceof cppParser.MethodDefContext methodCtx) {
                    boolean isVirtual = methodCtx.getText().startsWith("virtual");
                    Type returnType = new Type(methodCtx.OBJ_NAME(0).getText());
                    String methodName = methodCtx.OBJ_NAME(1).getText();
                    List<Parameter> params = parseParams(methodCtx.params());
                    Statement.Block body = visitBlock(methodCtx.block());
                    methods.add(new ClassMember.MethodDef(isVirtual, returnType, methodName, params, body));
                }
            }
        }

        return new Declaration.ClassDef(name, baseClass, fields, constructors, methods);
    }

    // Variable Declaration (top-level)
    @Override
    public Declaration.VarDecl visitVarDecl(cppParser.VarDeclContext ctx) {
        Type type = new Type(ctx.OBJ_NAME(0).getText());
        boolean isRef = ctx.getText().contains("&");
        String name = ctx.OBJ_NAME(1).getText();
        return new Declaration.VarDecl(type, isRef, name);
    }

    // Variable Definition (top-level)
    @Override
    public Declaration.VarDef visitVarDef(cppParser.VarDefContext ctx) {
        Type type = new Type(ctx.OBJ_NAME(0).getText());
        boolean isRef = ctx.getText().contains("&");
        String name = ctx.OBJ_NAME(1).getText();
        Expression init = (Expression) visit(ctx.value());
        return new Declaration.VarDef(type, isRef, name, init);
    }

    // --- STATEMENTS ---

    @Override
    public Statement.Block visitBlock(cppParser.BlockContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for (var stmtCtx : ctx.statement()) {
            Statement stmt = visitStatementInternal(stmtCtx);
            if (stmt != null) {
                statements.add(stmt);
            }
        }
        return new Statement.Block(statements);
    }

    // Hauptmethode für Statements - dispatcht zu den richtigen Submethoden
    private Statement visitStatementInternal(cppParser.StatementContext ctx) {
        if (ctx.varDef() != null) {
            var varDefCtx = ctx.varDef();
            Type type = new Type(varDefCtx.OBJ_NAME(0).getText());
            boolean isRef = varDefCtx.getText().contains("&");
            String name = varDefCtx.OBJ_NAME(1).getText();
            Expression init = (Expression) visit(varDefCtx.value());
            return new Statement.VarDef(type, isRef, name, init);
        } else if (ctx.varDecl() != null) {
            var varDeclCtx = ctx.varDecl();
            Type type = new Type(varDeclCtx.OBJ_NAME(0).getText());
            boolean isRef = varDeclCtx.getText().contains("&");
            String name = varDeclCtx.OBJ_NAME(1).getText();
            return new Statement.VarDecl(type, isRef, name);
        } else if (ctx.assignment() != null) {
            return visitAssignment(ctx.assignment());
        } else if (ctx.functionCallStmt() != null) {
            return visitFunctionCallStmt(ctx.functionCallStmt());
        } else if (ctx.ifStmt() != null) {
            return visitIfStmt(ctx.ifStmt());
        } else if (ctx.whileStmt() != null) {
            return visitWhileStmt(ctx.whileStmt());
        } else if (ctx.returnSTMT() != null) {
            return visitReturnSTMT(ctx.returnSTMT());
        } else if (ctx.block() != null) {
            return visitBlock(ctx.block());
        } else if (ctx.getText().equals(";")) {
            return new Statement.Empty();
        }
        return null;
    }

    @Override
    public Statement.Assignment visitAssignment(cppParser.AssignmentContext ctx) {
        Expression target;
        if (ctx.memberAccess() != null) {
            target = visitMemberAccess(ctx.memberAccess());
        } else {
            target = new Expression.Variable(ctx.OBJ_NAME().getText());
        }
        Expression value = (Expression) visit(ctx.value());
        return new Statement.Assignment(target, value);
    }

    @Override
    public Statement.FunctionCall visitFunctionCallStmt(cppParser.FunctionCallStmtContext ctx) {
        if (ctx.memberAccess() != null) {
            Expression.MemberAccess access = visitMemberAccess(ctx.memberAccess());
            List<Expression> args = parseCallArgs(ctx.callArgs());
            return new Statement.FunctionCall(access.memberName(), args);
        } else {
            String name = ctx.OBJ_NAME().getText();
            List<Expression> args = parseCallArgs(ctx.callArgs());
            return new Statement.FunctionCall(name, args);
        }
    }

    @Override
    public Statement.If visitIfStmt(cppParser.IfStmtContext ctx) {
        Expression condition = (Expression) visit(ctx.value());
        Statement thenBranch = visitStatementInternal(ctx.statement(0));
        Statement elseBranch = ctx.statement().size() > 1 ? visitStatementInternal(ctx.statement(1)) : null;
        return new Statement.If(condition, thenBranch, elseBranch);
    }

    @Override
    public Statement.While visitWhileStmt(cppParser.WhileStmtContext ctx) {
        Expression condition = (Expression) visit(ctx.value());
        Statement body = visitStatementInternal(ctx.statement());
        return new Statement.While(condition, body);
    }

    @Override
    public Statement.Return visitReturnSTMT(cppParser.ReturnSTMTContext ctx) {
        Expression value = ctx.value() != null ? (Expression) visit(ctx.value()) : null;
        return new Statement.Return(value);
    }

    // --- EXPRESSIONS ---

    @Override
    public Expression visitLogicalOr(cppParser.LogicalOrContext ctx) {
        if (ctx.logicalAnd().size() == 1) {
            return (Expression) visit(ctx.logicalAnd(0));
        }
        Expression left = (Expression) visit(ctx.logicalAnd(0));
        for (int i = 1; i < ctx.logicalAnd().size(); i++) {
            Expression right = (Expression) visit(ctx.logicalAnd(i));
            left = new Expression.BinaryOp(left, "||", right);
        }
        return left;
    }

    @Override
    public Expression visitLogicalAnd(cppParser.LogicalAndContext ctx) {
        if (ctx.comparison().size() == 1) {
            return (Expression) visit(ctx.comparison(0));
        }
        Expression left = (Expression) visit(ctx.comparison(0));
        for (int i = 1; i < ctx.comparison().size(); i++) {
            Expression right = (Expression) visit(ctx.comparison(i));
            left = new Expression.BinaryOp(left, "&&", right);
        }
        return left;
    }

    @Override
    public Expression visitComparison(cppParser.ComparisonContext ctx) {
        Expression left = (Expression) visit(ctx.math(0));
        if (ctx.math().size() == 1) {
            return left;
        }
        String op = ctx.getChild(1).getText();
        Expression right = (Expression) visit(ctx.math(1));
        return new Expression.BinaryOp(left, op, right);
    }

    @Override
    public Expression visitMath(cppParser.MathContext ctx) {
        if (ctx.multiplicative().size() == 1) {
            return (Expression) visit(ctx.multiplicative(0));
        }
        Expression left = (Expression) visit(ctx.multiplicative(0));
        for (int i = 1; i < ctx.multiplicative().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Expression right = (Expression) visit(ctx.multiplicative(i));
            left = new Expression.BinaryOp(left, op, right);
        }
        return left;
    }

    @Override
    public Expression visitMultiplicative(cppParser.MultiplicativeContext ctx) {
        if (ctx.unary().size() == 1) {
            return (Expression) visit(ctx.unary(0));
        }
        Expression left = (Expression) visit(ctx.unary(0));
        for (int i = 1; i < ctx.unary().size(); i++) {
            String op = ctx.getChild(i * 2 - 1).getText();
            Expression right = (Expression) visit(ctx.unary(i));
            left = new Expression.BinaryOp(left, op, right);
        }
        return left;
    }

    @Override
    public Expression visitUnary(cppParser.UnaryContext ctx) {
        if (ctx.getChildCount() == 1) {
            return (Expression) visit(ctx.primary());
        }
        String op = ctx.getChild(0).getText();
        Expression operand = (Expression) visit(ctx.primary());
        return new Expression.UnaryOp(op, operand);
    }

    @Override
    public Expression visitPrimary(cppParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        } else if (ctx.functionCallExpr() != null) {
            return visitFunctionCallExpr(ctx.functionCallExpr());
        } else if (ctx.memberAccess() != null) {
            return visitMemberAccess(ctx.memberAccess());
        } else if (ctx.OBJ_NAME() != null) {
            return new Expression.Variable(ctx.OBJ_NAME().getText());
        } else {
            // '(' value ')'
            return (Expression) visit(ctx.value());
        }
    }

    @Override
    public Expression visitLiteral(cppParser.LiteralContext ctx) {
        if (ctx.STRING_LITERAL() != null) {
            String text = ctx.STRING_LITERAL().getText();
            String value = text.substring(1, text.length() - 1);
            return new Expression.StringLiteral(value);
        } else if (ctx.intLiteral() != null) {
            return visitIntLiteral(ctx.intLiteral());
        } else if (ctx.boolLiteral() != null) {
            return visitBoolLiteral(ctx.boolLiteral());
        }
        return null;
    }

    @Override
    public Expression.IntLiteral visitIntLiteral(cppParser.IntLiteralContext ctx) {
        return new Expression.IntLiteral(Integer.parseInt(ctx.NUM().getText()));
    }

    @Override
    public Expression.BoolLiteral visitBoolLiteral(cppParser.BoolLiteralContext ctx) {
        return new Expression.BoolLiteral(ctx.getText().equals("true"));
    }

    @Override
    public Expression visitFunctionCallExpr(cppParser.FunctionCallExprContext ctx) {
        //  ctx kann entweder OBJ_NAME oder memberAccess haben
        if (ctx.memberAccess() != null) {
            // Methodenaufruf: p.getX()
            Expression.MemberAccess access = visitMemberAccess(ctx.memberAccess());
            List<Expression> args = parseCallArgs(ctx.callArgs());
            // Erstelle MethodCall statt FunctionCall ... leider bissl doof mit return type nur Expression, evtl hätte man einen gemeinsamen parent typ machen sollen
            return new Expression.MethodCall(access.object(), access.memberName(), args);
        } else {
            // Normaler Funktionsaufruf: add()
            String name = ctx.OBJ_NAME().getText();
            List<Expression> args = parseCallArgs(ctx.callArgs());
            return new Expression.FunctionCall(name, args);
        }
    }

    @Override
    public Expression.MemberAccess visitMemberAccess(cppParser.MemberAccessContext ctx) {
        Expression object = new Expression.Variable(ctx.OBJ_NAME(0).getText());
        String member = ctx.OBJ_NAME(1).getText();
        return new Expression.MemberAccess(object, member);
    }

    // Helper: Parse call arguments (für Funktionsaufrufe)
    // so koennen wir halt ne liste von expression zurückgeben, iwie geht das mit nem visitor nicht gut weil der nur 1 ASTNode zurückgeben kann
    private List<Expression> parseCallArgs(cppParser.CallArgsContext ctx) {
        List<Expression> args = new ArrayList<>();
        if (ctx.value() != null) {
            for (var valueCtx : ctx.value()) {
                args.add((Expression) visit(valueCtx));
            }
        }
        return args;
    }
}
