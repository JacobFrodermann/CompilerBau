package symbols;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import ast.Type;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Map<String, List<Symbol.FunctionSymbol>> functions = new HashMap<>();
    private final SymbolTable parent;
    private final String scopeName;

    public SymbolTable(String scopeName) {
        this(scopeName, null);
    }

    public SymbolTable(String scopeName, SymbolTable parent) {
        this.scopeName = scopeName;
        this.parent = parent;
    }

    public void define(Symbol symbol) throws SemanticException {
        String name = symbol.name();

        if (symbol instanceof Symbol.FunctionSymbol func) {
            List<Symbol.FunctionSymbol> overloads = functions.computeIfAbsent(name, k -> new ArrayList<>());

            // Prüfe auf doppelte Signatur
            Signature newSig = func.signature();
            for (Symbol.FunctionSymbol existing : overloads) {
                if (existing.signature().equals(newSig)) {
                    throw new SemanticException("Function already defined: " + newSig);
                }
            }
            overloads.add(func);
            symbols.put(name, func);

        } else {
            if (symbols.containsKey(name)) {
                throw new SemanticException("Symbol already defined: " + name);
            }
            symbols.put(name, symbol);
        }
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) return symbol;
        if (parent != null) return parent.resolve(name);
        return null;
    }

    public Symbol.FunctionSymbol resolveFunction(String name, List<Type> argTypes, List<Boolean> argRefs) {
        List<Symbol.FunctionSymbol> overloads = functions.get(name);
        if (overloads != null) {
            Signature sig = new Signature(name, argTypes, argRefs);
            for (Symbol.FunctionSymbol func : overloads) {
                if (func.signature().equals(sig)) {
                    return func;
                }
            }
        }

        if (parent != null) {
            return parent.resolveFunction(name, argTypes, argRefs);
        }
        return null;
    }

    public Map<String, Symbol> getAllSymbols() {
        return symbols;
    }

    public SymbolTable getParent() { return parent; }
    public String getScopeName() { return scopeName; }
}
