package symbols;

import ast.Parameter;
import ast.Type;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        // Für Overload-Resolution: Signatur-Check
        public String signature() {
            StringBuilder sb = new StringBuilder(name);
            sb.append("(");
            for (int i = 0; i < parameters.size(); i++) {
                if (i > 0) sb.append(",");
                Parameter p = parameters.get(i);
                sb.append(p.type().name());
                if (p.isReference()) sb.append("&");
            }
            sb.append(")");
            return sb.toString();
        }
    }

    // Class Symbol
    final class ClassSymbol implements Symbol {
        private final String name;
        private final String baseClassName;  // null wenn keine Vererbung
        private ClassSymbol baseClass;       // Wird in Pass 2 aufgelöst

        private final Map<String, VariableSymbol> fields = new HashMap<>();
        private final Map<String, FunctionSymbol> constructors = new HashMap<>();
        private final Map<String, FunctionSymbol> methods = new HashMap<>();
        private final Map<String, Boolean> virtualMethods = new HashMap<>();  // Name -> isVirtual

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

        public void addConstructor(FunctionSymbol ctor) {
            constructors.put(ctor.signature(), ctor);
        }

        public void addMethod(String name, FunctionSymbol method, boolean isVirtual) {
            methods.put(method.signature(), method);
            virtualMethods.put(name, isVirtual);
        }

        public VariableSymbol getField(String name) {
            VariableSymbol field = fields.get(name);
            if (field != null) return field;
            if (baseClass != null) return baseClass.getField(name);
            return null;
        }

        public FunctionSymbol getMethod(String signature) {
            FunctionSymbol method = methods.get(signature);
            if (method != null) return method;
            if (baseClass != null) return baseClass.getMethod(signature);
            return null;
        }

        public boolean isVirtual(String methodName) {
            Boolean v = virtualMethods.get(methodName);
            if (v != null && v) return true;
            if (baseClass != null) return baseClass.isVirtual(methodName);
            return false;
        }

        public Map<String, VariableSymbol> getAllFields() { return fields; }
        public Map<String, FunctionSymbol> getAllMethods() { return methods; }
    }
}
