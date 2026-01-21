package execution;

import ast.*;
import symbols.Symbol;
import symbols.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class Executor {
    private final SymbolTable globalSymbols;
    private Environment currentEnv;
    private ProgramNode program;
    private ObjectInstance currentObject;

    public Executor(SymbolTable globalSymbols) {
        this.globalSymbols = globalSymbols;
        this.currentEnv = new Environment(null);
    }

    public void execute(ProgramNode program) {
        this.program = program;
        Declaration.FunctionDef main = findMain();
        if (main == null) {
            throw new RuntimeException("No main() function found");
        }
        executeFunction(main, List.of());
    }

    private Declaration.FunctionDef findMain() {
        for (Declaration decl : program.declarations()) {
            if (decl instanceof Declaration.FunctionDef f) {
                if (f.name().equals("main")) {
                    return f;
                }
            }
        }
        return null;
    }

    private Declaration.FunctionDef findFunction(String name) {
        for (Declaration decl : program.declarations()) {
            if (decl instanceof Declaration.FunctionDef f) {
                if (f.name().equals(name)) {
                    return f;
                }
            }
        }
        return null;
    }

    private Declaration.ClassDef findClass(String name) {
        for (Declaration decl : program.declarations()) {
            if (decl instanceof Declaration.ClassDef c) {
                if (c.name().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    private Object executeFunction(Declaration.FunctionDef func, List<Object> argValues) {
        Environment funcEnv = new Environment(currentEnv);
        Environment oldEnv = currentEnv;
        currentEnv = funcEnv;

        for (int i = 0; i < func.parameters().size(); i++) {
            Parameter param = func.parameters().get(i);
            Object value = argValues.get(i);
            currentEnv.define(param.name(), value);
        }

        Object result = null;
        try {
            executeStatement(func.body());
        } catch (ReturnException e) {
            result = e.value;
        }

        currentEnv = oldEnv;
        return result;
    }

    private void executeStatement(Statement stmt) {
        switch (stmt) {
            case Statement.Block block -> {
                Environment blockEnv = new Environment(currentEnv);
                Environment oldEnv = currentEnv;
                currentEnv = blockEnv;

                for (Statement s : block.statements()) {
                    executeStatement(s);
                }

                currentEnv = oldEnv;
            }

            case Statement.VarDecl var -> {
                Object defaultVal = getDefaultValue(var.type().name());
                currentEnv.define(var.name(), defaultVal);
            }

            case Statement.VarDef var -> {
                Object value = evaluateExpression(var.initializer());
                currentEnv.define(var.name(), value);
            }

            case Statement.Assignment assign -> {
                Object value = evaluateExpression(assign.value());
                assignToLValue(assign.target(), value);
            }

            case Statement.FunctionCall call -> {
                evaluateFunctionCall(call.functionName(), call.arguments());
            }

            case Statement.If ifStmt -> {
                Object condValue = evaluateExpression(ifStmt.condition());
                if (toBoolean(condValue)) {
                    executeStatement(ifStmt.thenBranch());
                } else if (ifStmt.elseBranch() != null) {
                    executeStatement(ifStmt.elseBranch());
                }
            }

            case Statement.While whileStmt -> {
                while (true) {
                    Object condValue = evaluateExpression(whileStmt.condition());
                    if (!toBoolean(condValue)) break;
                    executeStatement(whileStmt.body());
                }
            }

            case Statement.Return ret -> {
                Object value = ret.value() != null
                    ? evaluateExpression(ret.value())
                    : null;
                throw new ReturnException(value);
            }

            case Statement.Empty empty -> {
                // Nichts tun
            }
        }
    }

    private Object evaluateExpression(Expression expr) {
        return switch (expr) {
            case Expression.IntLiteral lit -> lit.value();
            case Expression.BoolLiteral lit -> lit.value();
            case Expression.StringLiteral lit -> lit.value();

            case Expression.Variable var -> {
                // Lookup-Reihenfolge: lokale Variablen/Parameter -> eigene Members -> geerbte Members
                try {
                    yield currentEnv.get(var.name());
                } catch (RuntimeException e) {
                    // Falls nicht im lokalen Scope und wir in Methode: in Objekt-Feldern suchen
                    if (currentObject != null && currentObject.hasField(var.name())) {
                        yield currentObject.getField(var.name());
                    }
                    throw e;
                }
            }

            case Expression.MemberAccess access -> {
                Object obj = evaluateExpression(access.object());
                if (!(obj instanceof ObjectInstance objInst)) {
                    throw new RuntimeException("Cannot access member of non-object");
                }
                yield objInst.getField(access.memberName());
            }

            case Expression.FunctionCall call ->
                evaluateFunctionCall(call.functionName(), call.arguments());

            case Expression.MethodCall call -> {
                Object obj = evaluateExpression(call.object());
                if (!(obj instanceof ObjectInstance objInst)) {
                    throw new RuntimeException("Cannot call method on non-object");
                }
                yield evaluateMethodCall(objInst, call.methodName(), call.arguments());
            }

            case Expression.BinaryOp binOp -> {
                Object left = evaluateExpression(binOp.left());
                Object right = evaluateExpression(binOp.right());
                yield evaluateBinaryOp(binOp.operator(), left, right);
            }

            case Expression.UnaryOp unOp -> {
                Object operand = evaluateExpression(unOp.operand());
                yield evaluateUnaryOp(unOp.operator(), operand);
            }
        };
    }

    private Object evaluateFunctionCall(String name, List<Expression> arguments) {
        // Argumente auswerten
        List<Object> argValues = new ArrayList<>();
        for (Expression arg : arguments) {
            argValues.add(evaluateExpression(arg));
        }

        // Built-in Funktionen
        if (name.equals("print_int") || name.equals("print_bool") ||
            name.equals("print_string") || name.equals("print_char")) {
            System.out.println(argValues.get(0));
            return null;
        }

        // User-defined Funktion
        Declaration.FunctionDef func = findFunction(name);
        if (func == null) {
            throw new RuntimeException("Function not found: " + name);
        }

        return executeFunction(func, argValues);
    }

    private Object evaluateMethodCall(ObjectInstance obj, String methodName, List<Expression> arguments) {
        // Argumente auswerten
        List<Object> argValues = new ArrayList<>();
        for (Expression arg : arguments) {
            argValues.add(evaluateExpression(arg));
        }

        // Finde Methoden-Definition
        Declaration.ClassDef classDef = findClass(obj.getClassSymbol().name());
        if (classDef == null) {
            throw new RuntimeException("Class not found: " + obj.getClassSymbol().name());
        }

        // Suche Methode MIT TYPE-CHECKING (mit Vererbung)
        ClassMember.MethodDef method = findMethod(classDef, methodName, argValues);
        if (method == null) {
            throw new RuntimeException("Method not found: " + methodName);
        }

        // Führe Methode aus mit currentObject gesetzt
        Environment methodEnv = new Environment(currentEnv);
        Environment oldEnv = currentEnv;
        ObjectInstance oldObject = currentObject;
        currentEnv = methodEnv;
        currentObject = obj;

        // Parameter binden
        for (int i = 0; i < method.parameters().size(); i++) {
            Parameter param = method.parameters().get(i);
            Object value = argValues.get(i);
            currentEnv.define(param.name(), value);
        }

        // Body ausführen
        Object result = null;
        try {
            executeStatement(method.body());
        } catch (ReturnException e) {
            result = e.value;
        }

        currentEnv = oldEnv;
        currentObject = oldObject;
        return result;
    }

    private ClassMember.MethodDef findMethod(Declaration.ClassDef classDef, String name, List<Object> argValues) {
        // Suche in aktueller Klasse
        for (ClassMember.MethodDef method : classDef.methods()) {
            if (method.name().equals(name) && matchesSignature(method.parameters(), argValues)) {
                return method;
            }
        }

        // Suche in Basisklasse
        if (classDef.baseClass() != null) {
            Declaration.ClassDef baseClass = findClass(classDef.baseClass());
            if (baseClass != null) {
                return findMethod(baseClass, name, argValues);
            }
        }

        return null;
    }

    private boolean matchesSignature(List<Parameter> parameters, List<Object> argValues) {
        if (parameters.size() != argValues.size()) {
            return false;
        }

        for (int i = 0; i < parameters.size(); i++) {
            Parameter param = parameters.get(i);
            Object arg = argValues.get(i);

            String expectedType = param.type().name();
            String actualType = getTypeOfValue(arg);

            if (!expectedType.equals(actualType)) {
                return false;
            }
        }

        return true;
    }

    private String getTypeOfValue(Object value) {
        return switch (value) {
            case Integer i -> "int";
            case Boolean b -> "bool";
            case Character c -> "char";
            case String s -> "string";
            case ObjectInstance obj -> obj.getClassSymbol().name();
            case null -> throw new RuntimeException("Null value");
            default -> throw new RuntimeException("Unknown value type: " + value.getClass());
        };
    }

    private Object evaluateBinaryOp(String op, Object left, Object right) {
        if (left instanceof Integer l && right instanceof Integer r) {
            return switch (op) {
                case "+" -> l + r;
                case "-" -> l - r;
                case "*" -> l * r;
                case "/" -> {
                    if (r == 0) throw new RuntimeException("Division by zero");
                    yield l / r;
                }
                case "%" -> {
                    if (r == 0) throw new RuntimeException("Division by zero");
                    yield l % r;
                }
                case "==" -> l.equals(r);
                case "!=" -> !l.equals(r);
                case "<" -> l < r;
                case "<=" -> l <= r;
                case ">" -> l > r;
                case ">=" -> l >= r;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        if (left instanceof String l && right instanceof String r) {
            return switch (op) {
                case "+" -> l + r;
                case "==" -> l.equals(r);
                case "!=" -> !l.equals(r);
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        if (left instanceof Boolean l && right instanceof Boolean r) {
            return switch (op) {
                case "&&" -> l && r;
                case "||" -> l || r;
                case "==" -> l.equals(r);
                case "!=" -> !l.equals(r);
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        if (left instanceof Character l && right instanceof Character r) {
            return switch (op) {
                case "==" -> l.equals(r);
                case "!=" -> !l.equals(r);
                case "<" -> l < r;
                case "<=" -> l <= r;
                case ">" -> l > r;
                case ">=" -> l >= r;
                default -> throw new RuntimeException("Unknown operator: " + op);
            };
        }

        throw new RuntimeException("Type mismatch in binary operation");
    }

    private Object evaluateUnaryOp(String op, Object operand) {
        return switch (op) {
            case "-" -> -(Integer) operand;
            case "+" -> (Integer) operand;
            case "!" -> !(Boolean) operand;
            default -> throw new RuntimeException("Unknown unary operator: " + op);
        };
    }

    private void assignToLValue(Expression target, Object value) {
        switch (target) {
            case Expression.Variable var -> {
                // Lookup-Reihenfolge: lokale Variablen/Parameter vor Objekt-Felder
                try {
                    currentEnv.set(var.name(), value);
                } catch (RuntimeException e) {
                    // Falls nicht im lokalen Scope: Objekt-Feld
                    if (currentObject != null && currentObject.hasField(var.name())) {
                        currentObject.setField(var.name(), value);
                    } else {
                        throw e;
                    }
                }
            }
            case Expression.MemberAccess access -> {
                Object obj = evaluateExpression(access.object());
                if (!(obj instanceof ObjectInstance objInst)) {
                    throw new RuntimeException("Cannot assign to member of non-object");
                }
                objInst.setField(access.memberName(), value);
            }
            default -> throw new RuntimeException("Invalid lvalue");
        }
    }

    private Object getDefaultValue(String typeName) {
        return switch (typeName) {
            case "int" -> 0;
            case "bool" -> false;
            case "char" -> '\0';
            case "string" -> "";
            default -> {
                // Klassentyp - erstelle neues Objekt
                Symbol symbol = globalSymbols.resolve(typeName);
                if (symbol instanceof Symbol.ClassSymbol cls) {
                    ObjectInstance obj = new ObjectInstance(cls);
                    // Rufe parameterlosen Konstruktor auf
                    executeConstructor(obj, List.of());
                    yield obj;
                }
                throw new RuntimeException("Unknown type: " + typeName);
            }
        };
    }

    private void executeConstructor(ObjectInstance obj, List<Object> argValues) {
        Declaration.ClassDef classDef = findClass(obj.getClassSymbol().name());
        if (classDef == null) {
            return; // Kein Konstruktor definiert - Default-Initialisierung ist schon passiert
        }

        // Erst Basis-Konstruktor aufrufen (implizit, parameterlos)
        if (classDef.baseClass() != null) {
            Symbol baseSymbol = globalSymbols.resolve(classDef.baseClass());
            if (baseSymbol instanceof Symbol.ClassSymbol baseCls) {
                Declaration.ClassDef baseClassDef = findClass(baseCls.name());
                if (baseClassDef != null) {
                    executeConstructorBody(obj, baseClassDef, List.of());
                }
            }
        }

        // Dann eigenen Konstruktor
        executeConstructorBody(obj, classDef, argValues);
    }

    private void executeConstructorBody(ObjectInstance obj, Declaration.ClassDef classDef, List<Object> argValues) {
        // Finde passenden Konstruktor MIT TYPE-CHECKING
        ClassMember.ConstructorDef ctor = null;
        for (ClassMember.ConstructorDef c : classDef.constructors()) {
            if (matchesSignature(c.parameters(), argValues)) {
                ctor = c;
                break;
            }
        }

        if (ctor == null) {
            return; // Kein passender Konstruktor - Default-Initialisierung
        }

        // Führe Konstruktor aus
        Environment ctorEnv = new Environment(currentEnv);
        Environment oldEnv = currentEnv;
        ObjectInstance oldObject = currentObject;
        currentEnv = ctorEnv;
        currentObject = obj;

        // Parameter binden
        for (int i = 0; i < ctor.parameters().size(); i++) {
            Parameter param = ctor.parameters().get(i);
            Object value = argValues.get(i);
            currentEnv.define(param.name(), value);
        }

        try {
            executeStatement(ctor.body());
        } catch (ReturnException e) {
            // Konstruktor sollte nicht returnen, aber falls doch...
        }

        currentEnv = oldEnv;
        currentObject = oldObject;
    }

    private boolean toBoolean(Object value) {
        return switch (value) {
            case Boolean b -> b;
            case Integer i -> i != 0;
            case Character c -> c != '\0';
            case String s -> !s.isEmpty();
            default -> throw new RuntimeException("Cannot convert to boolean: " + value.getClass());
        };
    }

    // Exception für Return-Statements (Control-Flow)
    private static class ReturnException extends RuntimeException {
        final Object value;
        ReturnException(Object value) {
            super(null, null, false, false);
            this.value = value;
        }
    }
}
