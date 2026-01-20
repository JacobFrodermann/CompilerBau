package symbols;

import ast.Parameter;
import ast.Type;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

public sealed interface Symbol {
    String name();

    // Variable Symbol
    record VariableSymbol(String name, Type type, boolean isReference) implements Symbol {}

    // Function Symbol
    record FunctionSymbol(
        String name,
        Type returnType,
        List<Parameter> parameters
    ) implements Symbol {

        public Signature signature() {
            List<Type> types = parameters.stream()
                .map(Parameter::type)
                .collect(Collectors.toList());
            List<Boolean> refs = parameters.stream()
                .map(Parameter::isReference)
                .collect(Collectors.toList());
            return new Signature(name, types, refs);
        }
    }

    // Class Symbol
    final class ClassSymbol implements Symbol {
        private final String name;
        private final String baseClassName;
        private ClassSymbol baseClass;

        private final Map<String, VariableSymbol> fields = new HashMap<>();
        private final Map<Signature, FunctionSymbol> constructors = new HashMap<>();
        private final Map<String, List<FunctionSymbol>> methods = new HashMap<>();  // Name -> List für Overloading
        private final Map<String, Boolean> virtualMethods = new HashMap<>();

        public ClassSymbol(String name, String baseClassName) {
            this.name = name;
            this.baseClassName = baseClassName;
        }

        @Override
        public String name() { return name; }

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
