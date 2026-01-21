package symbols;

import ast.Declaration;
import ast.Parameter;
import ast.Statement;
import ast.Type;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

public sealed interface Symbol {
    String name();

    Object getValue();
    void storeValue(Object val);

    // Variable Symbol
    final class VariableSymbol implements Symbol {
        public String name;
        public Type type;
        public  boolean isReference;
        private Object value;

        public VariableSymbol(String name, Type type, boolean reference) {
           this.name = name;
           this.type = type;
           this.isReference = reference;
        }

        public  VariableSymbol(Statement.VarDecl decl) {
            name = decl.name();
            type = decl.type();
            isReference = decl.isReference();
        }
        public  VariableSymbol(Parameter p) {
            name = p.name();
            type = p.type();
            isReference = p.isReference();
        }

        public VariableSymbol(Statement.VarDef decl) {
            name = decl.name();
            type = decl.type();
            isReference = decl.isReference();
        }

        @Override
        public String name() { return name; }

        @Override
        public Object getValue() { return value; }

        @Override
        public void storeValue(Object val) { value = val; }

        public Type type() { return  type; }

        public boolean isReference() { return  isReference; }
    }

    // Function Symbol
    public final class FunctionSymbol implements Symbol {
        public String name;
        public Type returnType;
        public List<Parameter> parameters;
        public Object value;
        public Statement.Block block;

        public FunctionSymbol(String name, Type returnType, List<Parameter> parameters) {
            this.name = name;
            this.returnType = returnType;
            this.parameters = parameters;
        }

        public FunctionSymbol(String name, Type returnType, List<Parameter> parameters, Statement.Block block) {
            this.name = name;
            this.returnType = returnType;
            this.parameters = parameters;
            this.block = block;
        }

        public Signature signature() {
            List<Type> types = parameters.stream()
                .map(Parameter::type)
                .collect(Collectors.toList());
            List<Boolean> refs = parameters.stream()
                .map(Parameter::isReference)
                .collect(Collectors.toList());
            return new Signature(name, types, refs);
        }

        @Override
        public String name() { return name; }

        @Override
        public Object getValue() { return value; }

        @Override
        public void storeValue(Object val) { value = val; }

        public Type returnType() { return  returnType; }
    }

    // Class Symbol
    final class ClassSymbol implements Symbol {
        private final String name;
        private final String baseClassName;
        private ClassSymbol baseClass;
        Object value;

        private final Map<String, VariableSymbol> fields = new HashMap<>();
        private final Map<Signature, FunctionSymbol> constructors = new HashMap<>();
        private final Map<String, List<FunctionSymbol>> methods = new HashMap<>();  // Name -> List für Overloading
        private final Map<String, Boolean> virtualMethods = new HashMap<>();

        public ClassSymbol(String name, String baseClassName) {
            this.name = name;
            this.baseClassName = baseClassName;
        }

        public String name() { return name; }

        public Object getValue() { return value; }

        public void storeValue(Object val) { value = val; }

        public String baseClassName() { return baseClassName; }
        public ClassSymbol baseClass() { return baseClass; }
        public void setBaseClass(ClassSymbol base) { this.baseClass = base; }

        public void addField(String name, VariableSymbol field) {
            fields.put(name, field);
        }

        public void addConstructor(FunctionSymbol ctor) throws SemanticException {
            Signature sig = ctor.signature();
            if (constructors.containsKey(sig)) {
                throw new SemanticException("Constructor already defined: " + sig);
            }
            constructors.put(sig, ctor);
        }

        public void addMethod(String name, FunctionSymbol method, boolean isVirtual) throws SemanticException {
            List<FunctionSymbol> overloads = methods.computeIfAbsent(name, k -> new ArrayList<>());

            // Prüfe auf doppelte Signatur
            Signature newSig = method.signature();
            for (FunctionSymbol existing : overloads) {
                if (existing.signature().equals(newSig)) {
                    throw new SemanticException("Method already defined: " + newSig);
                }
            }

            overloads.add(method);
            virtualMethods.put(name, isVirtual);
        }

        public VariableSymbol getField(String name) {
            VariableSymbol field = fields.get(name);
            if (field != null) return field;
            if (baseClass != null) return baseClass.getField(name);
            return null;
        }

        public FunctionSymbol getMethod(String name, List<Type> argTypes, List<Boolean> argRefs) {
            List<FunctionSymbol> overloads = methods.get(name);
            if (overloads != null) {
                Signature sig = new Signature(name, argTypes, argRefs);
                for (FunctionSymbol method : overloads) {
                    if (method.signature().equals(sig)) {
                        return method;
                    }
                }
            }

            // Suche in Basisklasse
            if (baseClass != null) {
                return baseClass.getMethod(name, argTypes, argRefs);
            }
            return null;
        }

        public boolean isVirtual(String methodName) {
            Boolean v = virtualMethods.get(methodName);
            if (v != null && v) return true;
            if (baseClass != null) return baseClass.isVirtual(methodName);
            return false;
        }

        public Map<String, VariableSymbol> getAllFields() { return fields; }
        public Map<String, List<FunctionSymbol>> getAllMethods() { return methods; }
    }
}
