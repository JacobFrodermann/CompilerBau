package symbols;

import java.util.*;

import ast.Parameter;
import ast.Type;

public class SymbolTable {
    private final Map<String, Symbol> symbols = new HashMap<>();
    private final Map<String, List<Symbol.FunctionSymbol>> functions = new HashMap<>();
    private final SymbolTable parent;
    private final String scopeName;
    private int blocks = 0;

    public SymbolTable(String scopeName) {
        this(scopeName, null);
    }

    public final List<Symbol.FunctionSymbol> primitives = List.of(
        new Symbol.FunctionSymbol(
            "print_bool",
            new Type("void"),
            List.of(new Parameter(new Type("bool"), false, "value"))
        ),
        new Symbol.FunctionSymbol(
            "print_int",
            new Type("void"),
            List.of(new Parameter(new Type("int"), false, "value"))
        ),
        new Symbol.FunctionSymbol(
            "print_string",
            new Type("void"),
            List.of(
                new Parameter(new Type("string"), true, "value")
            )
        ),
        new Symbol.FunctionSymbol(
            "print_string",
            new Type("void"),
            List.of(
                new Parameter(new Type("string"), false, "value")
            )
        ),
        new Symbol.FunctionSymbol(
            "print_char",
            new Type("void"),
            List.of(new Parameter(new Type("char"), false, "value"))
        )
    );

    private void registerPrimitives() {
        for (Symbol.FunctionSymbol prim : primitives) {
            if (functions.get(prim.name) == null) {
                functions.put(prim.name, Arrays.asList(prim));
            } else {
                ArrayList<Symbol.FunctionSymbol> list = new ArrayList<>(functions.get(prim.name));
                list.add(prim);
                functions.put(prim.name, list);
            }
        }
    }

    public SymbolTable(String scopeName, SymbolTable parent) {
        this.scopeName = scopeName;
        this.parent = parent;

        registerPrimitives();
    }

    public SymbolTable block() {
        blocks ++;
        return new SymbolTable("block" + blocks, this);
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
