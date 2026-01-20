package symbols;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Map<String, List<Symbol.FunctionSymbol>> functions = new HashMap<>();  // Für Overloading
    private final SymbolTable parent;
    private final String scopeName;

    public SymbolTable(String scopeName) {
        this(scopeName, null);
    }

    public SymbolTable(String scopeName, SymbolTable parent) {
        this.scopeName = scopeName;
        this.parent = parent;
    }

    // Define Symbol
    public void define(Symbol symbol) throws SemanticException {
        String name = symbol.name();

        if (symbol instanceof Symbol.FunctionSymbol func) {
            // Funktionen: Overloading erlaubt, aber nur mit unterschiedlicher Signatur
            List<Symbol.FunctionSymbol> overloads = functions.computeIfAbsent(name, k -> new ArrayList<>());

            // Prüfe ob Signatur bereits existiert
            for (Symbol.FunctionSymbol existing : overloads) {
                if (existing.signature().equals(func.signature())) {
                    throw new SemanticException("Function already defined: " + func.signature());
                }
            }
            overloads.add(func);
            symbols.put(name, func);  // Für einfache Lookup

        } else {
            // Variablen und Klassen: Keine Mehrfachdefinition
            if (symbols.containsKey(name)) {
                throw new SemanticException("Symbol already defined: " + name);
            }
            symbols.put(name, symbol);
        }
    }

    // Resolve Symbol
    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) return symbol;
        if (parent != null) return parent.resolve(name);
        return null;
    }

    // Resolve Function mit Overload-Resolution
    public Symbol.FunctionSymbol resolveFunction(String name, List<ast.Type> argTypes, List<Boolean> argRefs) {
        List<Symbol.FunctionSymbol> overloads = functions.get(name);
        if (overloads == null) {
            if (parent != null) return parent.resolveFunction(name, argTypes, argRefs);
            return null;
        }

        // Suche exakten Match
        for (Symbol.FunctionSymbol func : overloads) {
            if (matchesSignature(func, argTypes, argRefs)) {
                return func;
            }
        }

        // Kein Match in diesem Scope, prüfe Parent
        if (parent != null) return parent.resolveFunction(name, argTypes, argRefs);
        return null;
    }

    private boolean matchesSignature(Symbol.FunctionSymbol func, List<ast.Type> argTypes, List<Boolean> argRefs) {
        if (func.parameters().size() != argTypes.size()) return false;

        for (int i = 0; i < argTypes.size(); i++) {
            ast.Parameter param = func.parameters().get(i);
            if (!param.type().name().equals(argTypes.get(i).name())) return false;
            if (param.isReference() != argRefs.get(i)) return false;
        }
        return true;
    }

    public Map<String, Symbol> getAllSymbols() {
        return symbols;
    }

    public SymbolTable getParent() { return parent; }
    public String getScopeName() { return scopeName; }
}
