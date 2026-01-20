package analysis;

import ast.*;
import symbols.*;
import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {
    private final SymbolTable globalSymbols;
    private SymbolTable currentScope;
    private Symbol.ClassSymbol currentClass;
    private Type currentFunctionReturnType;

    public SemanticAnalyzer(SymbolTable globalSymbols) {
        this.globalSymbols = globalSymbols;
        this.currentScope = globalSymbols;
    }

    public void analyze(ProgramNode program) throws SemanticException {
        for (Declaration decl : program.declarations()) {
            analyzeDeclaration(decl);
        }
    }

    private void analyzeDeclaration(Declaration decl) throws SemanticException {
        switch (decl) {
            case Declaration.FunctionDef func -> analyzeFunctionDef(func);
            case Declaration.ClassDef cls -> analyzeClassDef(cls);
            case Declaration.VarDef var -> {
                // Prüfe Initialisierer-Typ
                Type exprType = analyzeExpression(var.initializer());
                if (!typesMatch(var.type(), exprType)) {
                    throw new SemanticException("Type mismatch in variable definition: expected " +
                        var.type().name() + ", got " + exprType.name());
                }

                // Prüfe Referenz-Initialisierung
                if (var.isReference() && !isLValue(var.initializer())) {
                    throw new SemanticException("Reference must be initialized with an lvalue");
                }
            }
            case Declaration.VarDecl var -> {
                // Nur Deklaration, nichts zu prüfen
            }
            case Declaration.FunctionDecl func -> {
                // Nur Deklaration, nichts zu prüfen
            }
        }
    }

    private void analyzeFunctionDef(Declaration.FunctionDef func) throws SemanticException {
        // Neuer Scope für Funktion
        SymbolTable funcScope = new SymbolTable(func.name(), currentScope);
        SymbolTable oldScope = currentScope;
        currentScope = funcScope;
        currentFunctionReturnType = func.returnType();

        // Parameter in Scope aufnehmen
        for (Parameter param : func.parameters()) {
            Symbol.VariableSymbol paramSymbol = new Symbol.VariableSymbol(
                param.name(),
                param.type(),
                param.isReference()
            );
            funcScope.define(paramSymbol);
        }

        // Body analysieren
        // hier direkt die statements aus dem body weil wir sonst in der andereen methode nochmal ein neuen scope machen würden was wir nicht wollen
        for (Statement stmt : func.body().statements()) {
            analyzeStatement(stmt);
        }

        currentScope = oldScope;
        currentFunctionReturnType = null;
    }

    private void analyzeClassDef(Declaration.ClassDef cls) throws SemanticException {
        Symbol classSymbol = globalSymbols.resolve(cls.name());
        if (!(classSymbol instanceof Symbol.ClassSymbol)) {
            throw new SemanticException("Class symbol not found: " + cls.name());
        }

        currentClass = (Symbol.ClassSymbol) classSymbol;

        // Prüfe Felder auf Name-Clashes
        checkFieldNameClashes(currentClass);

        // Konstruktoren analysieren
        for (ClassMember.ConstructorDef ctor : cls.constructors()) {
            analyzeConstructor(ctor);
        }

        // Methoden analysieren
        for (ClassMember.MethodDef method : cls.methods()) {
            analyzeMethod(method);
        }

        currentClass = null;
    }

    private void checkFieldNameClashes(Symbol.ClassSymbol cls) throws SemanticException {
        // Prüfe ob Felder und Methoden denselben Namen haben
        for (String fieldName : cls.getAllFields().keySet()) {
            for (var method : cls.getAllMethods().values()) {
                if (method.name().equals(fieldName)) {
                    throw new SemanticException("Name clash: field and method both named '" + fieldName + "'");
                }
            }
        }
    }

    private void analyzeConstructor(ClassMember.ConstructorDef ctor) throws SemanticException {
        if (!ctor.name().equals(currentClass.name())) {
            throw new SemanticException("Constructor name must match class name");
        }

        SymbolTable ctorScope = new SymbolTable("ctor:" + ctor.name(), currentScope);
        SymbolTable oldScope = currentScope;
        currentScope = ctorScope;
        currentFunctionReturnType = new Type(currentClass.name());

        for (Parameter param : ctor.parameters()) {
            Symbol.VariableSymbol paramSymbol = new Symbol.VariableSymbol(
                param.name(),
                param.type(),
                param.isReference()
            );
            ctorScope.define(paramSymbol);
        }

        for (Statement stmt : ctor.body().statements()) {
            analyzeStatement(stmt);
        }

        currentScope = oldScope;
        currentFunctionReturnType = null;
    }

    private void analyzeMethod(ClassMember.MethodDef method) throws SemanticException {
        SymbolTable methodScope = new SymbolTable("method:" + method.name(), currentScope);
        SymbolTable oldScope = currentScope;
        currentScope = methodScope;
        currentFunctionReturnType = method.returnType();

        for (Parameter param : method.parameters()) {
            Symbol.VariableSymbol paramSymbol = new Symbol.VariableSymbol(
                param.name(),
                param.type(),
                param.isReference()
            );
            methodScope.define(paramSymbol);
        }

        for (Statement stmt : method.body().statements()) {
            analyzeStatement(stmt);
        }

        currentScope = oldScope;
        currentFunctionReturnType = null;
    }
    private void analyzeStatement(Statement stmt) throws SemanticException {
        switch (stmt) {
            case Statement.Block block -> {
                // Neuer Scope für Block
                SymbolTable blockScope = new SymbolTable("block", currentScope);
                SymbolTable oldScope = currentScope;
                currentScope = blockScope;

                for (Statement s : block.statements()) {
                    analyzeStatement(s);
                }

                currentScope = oldScope;
            }

            case Statement.VarDecl var -> {
                // Variable in Scope aufnehmen
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(
                    var.name(),
                    var.type(),
                    var.isReference()
                );
                currentScope.define(symbol);
            }

            case Statement.VarDef var -> {
                // Typ-Check
                Type exprType = analyzeExpression(var.initializer());
                if (!typesMatch(var.type(), exprType)) {
                    throw new SemanticException("Type mismatch: expected " + var.type().name() +
                        ", got " + exprType.name());
                }

                // Referenz-Check
                if (var.isReference() && !isLValue(var.initializer())) {
                    throw new SemanticException("Reference must be initialized with an lvalue");
                }

                // In Scope aufnehmen
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(
                    var.name(),
                    var.type(),
                    var.isReference()
                );
                currentScope.define(symbol);
            }

            case Statement.Assignment assign -> {
                // LValue-Check
                if (!isLValue(assign.target())) {
                    throw new SemanticException("Left side of assignment must be an lvalue");
                }

                // Typ-Check
                Type targetType = analyzeExpression(assign.target());
                Type valueType = analyzeExpression(assign.value());

                if (!typesMatch(targetType, valueType)) {
                    throw new SemanticException("Type mismatch in assignment: expected " +
                        targetType.name() + ", got " + valueType.name());
                }
            }

            case Statement.FunctionCall call -> {
                analyzeFunctionCall(call.functionName(), call.arguments());
            }

            case Statement.If ifStmt -> {
                Type condType = analyzeExpression(ifStmt.condition());
                // Implizite Konversion zu bool nur für int, char, string
                if (!condType.name().matches("bool|int|char|string")) {
                    throw new SemanticException("If condition must be bool, int, char, or string");
                }

                analyzeStatement(ifStmt.thenBranch());
                if (ifStmt.elseBranch() != null) {
                    analyzeStatement(ifStmt.elseBranch());
                }
            }

            case Statement.While whileStmt -> {
                Type condType = analyzeExpression(whileStmt.condition());
                // Implizite Konversion zu bool nur für int, char, string
                if (!condType.name().matches("bool|int|char|string")) {
                    throw new SemanticException("While condition must be bool, int, char, or string");
                }

                analyzeStatement(whileStmt.body());
            }

            case Statement.Return ret -> {
                if (ret.value() == null) {
                    if (!currentFunctionReturnType.name().equals("void")) {
                        throw new SemanticException("Non-void function must return a value");
                    }
                } else {
                    Type retType = analyzeExpression(ret.value());
                    if (!typesMatch(currentFunctionReturnType, retType)) {
                        throw new SemanticException("Return type mismatch: expected " +
                            currentFunctionReturnType.name() + ", got " + retType.name());
                    }
                }
            }

            case Statement.Empty empty -> {
                // Nichts zu tun ... xd
            }
        }
    }

    private Type analyzeExpression(Expression expr) throws SemanticException {
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
                Type objType = analyzeExpression(access.object());

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
                yield analyzeFunctionCall(call.functionName(), call.arguments());
            }

            case Expression.BinaryOp binOp -> {
                Type leftType = analyzeExpression(binOp.left());
                Type rightType = analyzeExpression(binOp.right());

                String op = binOp.operator();

                // Arithmetik: int only
                if (op.matches("[+\\-*/%]")) {
                    if (!leftType.name().equals("int") || !rightType.name().equals("int")) {
                        throw new SemanticException("Arithmetic operators require int operands");
                    }
                    yield new Type("int");
                }

                // Vergleich
                if (op.matches("==|!=|<|<=|>|>=")) {
                    if (!typesMatch(leftType, rightType)) {
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
                Type operandType = analyzeExpression(unOp.operand());
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
        };
    }

    private Type analyzeFunctionCall(String name, List<Expression> arguments) throws SemanticException {
        // Argument-Typen ermitteln
        List<Type> argTypes = new ArrayList<>();
        List<Boolean> argRefs = new ArrayList<>();

        for (Expression arg : arguments) {
            Type argType = analyzeExpression(arg);
            argTypes.add(argType);
            argRefs.add(isLValue(arg));
        }

        // Funktion suchen (mit Overload-Resolution)
        Symbol.FunctionSymbol func = currentScope.resolveFunction(name, argTypes, argRefs);

        // Prüfe auch in Klassen-Methoden
        if (func == null && currentClass != null) {
            // Baue Signatur
            StringBuilder sig = new StringBuilder(name);
            sig.append("(");
            for (int i = 0; i < argTypes.size(); i++) {
                if (i > 0) sig.append(",");
                sig.append(argTypes.get(i).name());
                if (argRefs.get(i)) sig.append("&");
            }
            sig.append(")");

            func = currentClass.getMethod(sig.toString());
        }

        if (func == null) {
            throw new SemanticException("Function not found: " + name);
        }

        return func.returnType();
    }

    private boolean isLValue(Expression expr) {
        return expr instanceof Expression.Variable || expr instanceof Expression.MemberAccess;
    }

    private boolean typesMatch(Type t1, Type t2) {
        return t1.name().equals(t2.name());
    }
}
