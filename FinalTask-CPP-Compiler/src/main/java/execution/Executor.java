package execution;

import analysis.SemanticAnalyzer;
import ast.*;
import symbols.SemanticException;
import symbols.Signature;
import symbols.Symbol;
import symbols.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class Executor {
    private final SymbolTable globalSymbols;
    private SymbolTable currentScope;
    private Symbol.ClassSymbol currentClass;
    private ProgramNode topLevel;
    Type currentFunctionReturnType;

    public Executor(SymbolTable globalSymbols) {
        this.globalSymbols = globalSymbols;
        this.currentScope = globalSymbols;
    }

    public Declaration.FunctionDef getMain(ProgramNode node) {
        for (Declaration d : node.declarations()) {
            if (d instanceof Declaration.FunctionDef f) {
                if (f.name().equals("main") && (f.returnType().name().equals("void") || f.returnType().name().equals("int"))) {
                    return f;
                }
            }
        }
        return null;
    }

    boolean handlePrimitive(Symbol.FunctionSymbol func, List<Expression> arguments) throws SemanticException {
        if (!func.returnType().name().equals("void")) return false;
       for (Symbol.FunctionSymbol prim : globalSymbols.primitives) {
           //IO.println(func.signature() + " == " + prim.signature());
           //IO.println(func.signature().hashCode() + " == " + prim.signature().hashCode());
           if (func.signature().equals(prim.signature())) {
                IO.println(executeExpression(arguments.getFirst()));
                return true;
            }
       }
        return false;
    }

    Object executeFunc(Symbol.FunctionSymbol func, List<Expression> arguments) throws SemanticException {
        if (func.block == null) throw new SemanticException("Function without definition was called");
        return  executeFunc(new Declaration.FunctionDef(func.returnType, func.name, func.parameters, func.block), arguments);
    }
    Object executeFunc(Declaration.FunctionDef func, List<Expression> arguments) throws SemanticException {
        currentFunctionReturnType= func.returnType();
        SymbolTable callerScope = currentScope;
        currentScope = new SymbolTable(func.name(), callerScope);

        for (int i = 0; i < func.parameters().size(); i ++) {
            Symbol.VariableSymbol paramSymbol = new Symbol.VariableSymbol(
                func.parameters().get(i).name(),
                func.parameters().get(i).type(),
                func.parameters().get(i).isReference()
            );
            paramSymbol.storeValue(executeExpression(arguments.get(i)));
            currentScope.define(paramSymbol);
        }

        Object ret = null;
        for (Statement stmt : func.body().statements()) {
            ret = executeStatement(stmt);
            // dont execute past return statement
            if (ret != null) {
                break;
            }
        }
        currentScope = callerScope;
        return ret;
    }

    Object executeStatement(Statement stmt) throws SemanticException {
        switch (stmt) {
            case Statement.VarDecl var -> {
                // Variable in Scope aufnehmen
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(var);
                currentScope.define(symbol);
            }

            case Statement.VarDef var -> {
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(var);
                symbol.storeValue(executeExpression(var.initializer()));
                currentScope.define(symbol);
            }

            case Statement.FunctionCall call -> {
                List<Type> argTypes = new ArrayList<>();
                List<Boolean> argRefs = new ArrayList<>();

                for (Expression arg : call.arguments()) {
                    Type argType = getTypeOfExpr(arg);
                    argTypes.add(argType);
                    argRefs.add(SemanticAnalyzer.isLValue(arg));
                }
                Symbol.FunctionSymbol sym = currentScope.resolveFunction(call.functionName(), argTypes, argRefs);
                boolean isPrim = handlePrimitive(sym, call.arguments());
                if (!isPrim)  {
                    executeFunc(sym, call.arguments());
                }
            }

            case Statement.Return ret -> {
                if (currentFunctionReturnType.name().equals("void")) return null;
                return executeExpression(ret.value());
            }

            case Statement.If branch -> {
                Type condType = getTypeOfExpr(branch.condition());

                Object ret;
                if (ExpressionToBool(condType, branch.condition())) {
                    ret = executeStatement(branch.thenBranch());
                } else {
                    ret = executeStatement(branch.elseBranch());
                }
                if (ret != null) {
                    return  ret;
                }
            }

            case Statement.Empty e -> {}
            case Statement.While loop -> {
                Type condType = getTypeOfExpr(loop.condition());

                while (ExpressionToBool(condType, loop.condition())) {
                    Object ret = executeStatement(loop.body());
                    if (ret !=  null) {
                        return ret;
                    }
                }
            }


            case Statement.Block block -> {
                SymbolTable callerScope = currentScope;
                currentScope = currentScope.block();

                Object ret = null;
                for (var stamtement : block.statements()) {
                    ret = executeStatement(stamtement);
                    if (ret != null) {
                        currentScope = callerScope;
                        return ret;
                    }
                }
                currentScope = callerScope;
            }
            default -> throw new UnsupportedOperationException("Unexpected value: " + stmt);
        }
        return null;
    }

    Type getTypeOfExpr(Expression expr) throws SemanticException {
        return switch (expr) {
            case Expression.IntLiteral lit -> new Type("int");
            case Expression.BoolLiteral lit -> new Type("bool");
            case Expression.StringLiteral lit -> new Type("string");

            case Expression.Variable var -> {
                Symbol symbol = currentScope.resolve(var.name());

                // Prüfe auch in Klassen-Feldern
                if (symbol == null && currentClass != null) {
                    Symbol.VariableSymbol field = currentClass.getField(var.name());
                    if (field != null) {
                        yield field.type();
                    }
                }

                if (symbol == null) {
                    throw new SemanticException("Undefined variable: " + var.name());
                }

                if (!(symbol instanceof Symbol.VariableSymbol)) {
                    throw new SemanticException("Not a variable: " + var.name());
                }

                yield ((Symbol.VariableSymbol) symbol).type();
            }

            case Expression.MemberAccess access -> {
                Type objType = getTypeOfExpr(access.object());

                // Prüfe ob objType eine Klasse ist
                Symbol classSymbol = globalSymbols.resolve(objType.name());
                if (!(classSymbol instanceof Symbol.ClassSymbol)) {
                    throw new SemanticException("Cannot access member of non-class type");
                }

                Symbol.ClassSymbol cls = (Symbol.ClassSymbol) classSymbol;
                Symbol.VariableSymbol field = cls.getField(access.memberName());

                if (field == null) {
                    throw new SemanticException("Field not found: " + access.memberName());
                }

                yield field.type();
            }

            case Expression.FunctionCall call -> {
                yield getTypeOfFunctionCall(call.functionName(), call.arguments());
            }

            case Expression.MethodCall call -> {
                Type objType = getTypeOfExpr(call.object());

                Symbol classSymbol = globalSymbols.resolve(objType.name());
                if (!(classSymbol instanceof Symbol.ClassSymbol)) {
                    throw new SemanticException("Cannot call method on non-class type");
                }

                Symbol.ClassSymbol cls = (Symbol.ClassSymbol) classSymbol;

                List<Type> argTypes = new ArrayList<>();
                List<Boolean> argRefs = new ArrayList<>();
                for (Expression arg : call.arguments()) {
                    argTypes.add(getTypeOfExpr(arg));
                    argRefs.add(SemanticAnalyzer.isLValue(arg));
                }

                Symbol.FunctionSymbol method = cls.getMethod(call.methodName(), argTypes, argRefs);
                if (method == null) {
                    throw new SemanticException("Method not found: " + call.methodName());
                }

                yield method.returnType();
            }

            case Expression.BinaryOp binOp -> {
                Type leftType = getTypeOfExpr(binOp.left());
                Type rightType = getTypeOfExpr(binOp.right());

                String op = binOp.operator();

                if (op.equals("+")) {
                    if (!((leftType.name().equals("int") && rightType.name().equals("int")) || (leftType.name().equals("string") && !rightType.name().equals("string")))) {
                        throw new SemanticException("Arithmetic operators require int operands3 got " + leftType.name() + " and " + rightType.name());
                    }
                    yield leftType;
                }

                // Arithmetik: int only
                if (op.matches("[\\-*/%]")) {
                    if (!leftType.name().equals("int") || !rightType.name().equals("int")) {
                        throw new SemanticException("Arithmetic operators require int operands2");
                    }
                    yield new Type("int");
                }

                // Vergleich
                if (op.matches("==|!=|<|<=|>|>=")) {
                    if (!SemanticAnalyzer.typesMatch(leftType, rightType)) {
                        throw new SemanticException("Comparison requires same types");
                    }
                    yield new Type("bool");
                }

                // Logik
                if (op.matches("&&|\\|\\|")) {
                    if (!leftType.name().equals("bool") || !rightType.name().equals("bool")) {
                        throw new SemanticException("Logical operators require bool operands");
                    }
                    yield new Type("bool");
                }

                throw new SemanticException("Unknown operator: " + op);
            }

            case Expression.UnaryOp unOp -> {
                Type operandType = getTypeOfExpr(unOp.operand());
                String op = unOp.operator();

                if (op.equals("!")) {
                    if (!operandType.name().equals("bool")) {
                        throw new SemanticException("Logical NOT requires bool operand");
                    }
                    yield new Type("bool");
                }

                if (op.equals("+") || op.equals("-")) {
                    if (!operandType.name().equals("int")) {
                        throw new SemanticException("Unary +/- requires int operand");
                    }
                    yield new Type("int");
                }

                throw new SemanticException("Unknown unary operator: " + op);
            }

            default -> {throw new SemanticException("shouldnt happen");}
        };
    }

    private Type getTypeOfFunctionCall(String name, List<Expression> arguments) throws SemanticException {
        // Argument-Typen ermitteln
        List<Type> argTypes = new ArrayList<>();
        List<Boolean> argRefs = new ArrayList<>();

        for (Expression arg : arguments) {
            Type argType = getTypeOfExpr(arg);
            argTypes.add(argType);
            argRefs.add(SemanticAnalyzer.isLValue(arg));
        }

        // Funktion suchen
        Symbol.FunctionSymbol func = currentScope.resolveFunction(name, argTypes, argRefs);

        // Prüfe auch in Klassen-Methoden
        if (func == null && currentClass != null) {
            func = currentClass.getMethod(name, argTypes, argRefs);
        }

        if (func == null) {
            throw new SemanticException("Function not found: " + name +
                " with signature " + new Signature(name, argTypes, argRefs));
        }

        return func.returnType();
    }

    Object executeExpression(Expression expr) throws SemanticException {
        switch (expr) {
            case Expression.Literal l -> {
                return l.value();
            }
            case Expression.BinaryOp binaryOp -> {
                if (typeOfExprStr(binaryOp.left()).equals("int") && typeOfExprStr(binaryOp.right()).equals("int")) {
                    Integer l = (Integer) executeExpression(binaryOp.left()), r = (Integer) executeExpression(binaryOp.right());
                    switch (binaryOp.operator()) {
                        case "+" -> {
                            return l + r;
                        }
                        case "-" -> {
                            return  l - r;
                        }
                        case "*" -> {
                            return  l * r;
                        }
                        case "/" -> {
                            return  l / r;
                        }
                        case "%" -> {
                            return l % r;
                        }
                        case ">" -> {
                            return l > r;
                        }
                        case "<" -> {
                            return l < r;
                        }
                        case "<=" -> {
                            return l <= r;
                        }
                        case ">=" -> {
                            return l >= r;
                        }
                        default -> {
                            throw new UnsupportedOperationException(binaryOp.operator());
                        }
                    }
                }
                if (typeOfExprStr(binaryOp.left()).equals("string") && typeOfExprStr(binaryOp.right()).equals("string")) {
                    String l = (String) executeExpression(binaryOp.left()), r = (String) executeExpression(binaryOp.right());
                    if (binaryOp.operator().equals("+")) {
                        return l + r;
                    }
                }
            }
            case Expression.FunctionCall call -> {
                List<Type> argTypes = new ArrayList<>();
                List<Boolean> argRefs = new ArrayList<>();

                for (Expression arg : call.arguments()) {
                    Type argType = getTypeOfExpr(arg);
                    argTypes.add(argType);
                    argRefs.add(SemanticAnalyzer.isLValue(arg));
                }
                Symbol.FunctionSymbol sym = currentScope.resolveFunction(call.functionName(), argTypes, argRefs);
                boolean isPrim = handlePrimitive(sym, call.arguments());

                if (!isPrim)  {
                    return executeFunc(sym, call.arguments());
                }
            }
            case Expression.MemberAccess memberAccess -> {
            }
            case Expression.MethodCall methodCall -> {
            }
            case Expression.UnaryOp unaryOp -> {
                switch (unaryOp.operator()) {
                    case "-" -> {
                        // TODO
                    }
                }
            }
            case Expression.Variable variable -> {
                return currentScope.resolve(variable.name()).getValue();
            }
        }
        return null;
    }

    public void execute(ProgramNode node) throws SemanticException {
        topLevel = node;
        Declaration.FunctionDef entry = getMain(node);

        executeFunc(entry, List.of());
    }

    String typeOfExprStr(Expression expr) throws SemanticException {
       return getTypeOfExpr(expr).name();
    }

    Declaration.FunctionDef getDefinitionFromCall(Statement.FunctionCall call) {
        for (Declaration d : topLevel.declarations()) {
            if (d instanceof Declaration.FunctionDef f) {
                if (f.name() == call.functionName()) {
                    return f;
                }
            }
        }
        return null;

    }

    boolean ExpressionToBool(Type t, Expression e) throws SemanticException {
        boolean descicion = switch (t.name()) {
            case "string" -> !((String) executeExpression(e)).isEmpty();
            case "bool" -> (Boolean) executeExpression(e);
            case "char" -> ((Character) executeExpression(e)) != '\0';
            case "int" -> ((Integer) executeExpression(e) != 0);
            default -> throw new IllegalStateException("Illeagel type in if condition: " + condType.name());
        };

    }
}
