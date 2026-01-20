package analysis;

import ast.*;
import symbols.*;

public class SymbolCollector {
    private final SymbolTable globalSymbols;

    public SymbolCollector() {
        this.globalSymbols = new SymbolTable("global");
    }

    public SymbolTable collect(ProgramNode program) throws SemanticException {
        // Pass 1: Sammle alle Klassen und Funktionen (define-after-use erlaubt)
        for (Declaration decl : program.declarations()) {
            collectDeclaration(decl);
        }

        // Nach dem Sammeln: Löse Vererbungshierarchie auf
        resolveInheritance();

        return globalSymbols;
    }

    private void collectDeclaration(Declaration decl) throws SemanticException {
        switch (decl) {
            case Declaration.FunctionDef func -> {
                Symbol.FunctionSymbol symbol = new Symbol.FunctionSymbol(
                    func.name(),
                    func.returnType(),
                    func.parameters()
                );
                globalSymbols.define(symbol);
            }

            case Declaration.FunctionDecl func -> {
                Symbol.FunctionSymbol symbol = new Symbol.FunctionSymbol(
                    func.name(),
                    func.returnType(),
                    func.parameters()
                );
                globalSymbols.define(symbol);
            }

            case Declaration.ClassDef cls -> {
                Symbol.ClassSymbol classSymbol = new Symbol.ClassSymbol(cls.name(), cls.baseClass());

                // Felder sammeln
                for (ClassMember.FieldDecl field : cls.fields()) {
                    Symbol.VariableSymbol fieldSymbol = new Symbol.VariableSymbol(
                        field.name(),
                        field.type(),
                        false  // Felder können keine Referenzen sein
                    );
                    classSymbol.addField(field.name(), fieldSymbol);
                }

                // Konstruktoren sammeln
                for (ClassMember.ConstructorDef ctor : cls.constructors()) {
                    Symbol.FunctionSymbol ctorSymbol = new Symbol.FunctionSymbol(
                        ctor.name(),
                        new Type(cls.name()),  // Rückgabetyp = Klassentyp
                        ctor.parameters()
                    );
                    classSymbol.addConstructor(ctorSymbol);
                }

                // Methoden sammeln
                for (ClassMember.MethodDef method : cls.methods()) {
                    Symbol.FunctionSymbol methodSymbol = new Symbol.FunctionSymbol(
                        method.name(),
                        method.returnType(),
                        method.parameters()
                    );
                    classSymbol.addMethod(method.name(), methodSymbol, method.isVirtual());
                }

                globalSymbols.define(classSymbol);
            }

            case Declaration.VarDecl var -> {
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(
                    var.name(),
                    var.type(),
                    var.isReference()
                );
                globalSymbols.define(symbol);
            }

            case Declaration.VarDef var -> {
                Symbol.VariableSymbol symbol = new Symbol.VariableSymbol(
                    var.name(),
                    var.type(),
                    var.isReference()
                );
                globalSymbols.define(symbol);
            }
        }
    }

    private void resolveInheritance() throws SemanticException {
        // Durchlaufe alle Klassen und setze baseClass-Referenzen
        for (var entry : globalSymbols.getAllSymbols().entrySet()) {
            Symbol symbol = entry.getValue();

            if (symbol instanceof Symbol.ClassSymbol classSymbol) {
                String baseClassName = classSymbol.baseClassName();

                if (baseClassName != null) {
                    Symbol baseSymbol = globalSymbols.resolve(baseClassName);

                    if (baseSymbol == null) {
                        throw new SemanticException("Base class not found: " + baseClassName);
                    }

                    if (!(baseSymbol instanceof Symbol.ClassSymbol)) {
                        throw new SemanticException("Base must be a class: " + baseClassName);
                    }

                    classSymbol.setBaseClass((Symbol.ClassSymbol) baseSymbol);

                    // Prüfe auf Zyklen
                    checkInheritanceCycles(classSymbol);
                }
            }
        }
    }

    private void checkInheritanceCycles(Symbol.ClassSymbol classSymbol) throws SemanticException {
        Symbol.ClassSymbol current = classSymbol.baseClass();

        while (current != null) {
            if (current == classSymbol) {
                throw new SemanticException("Inheritance cycle detected for class: " + classSymbol.name());
            }
            current = current.baseClass();
        }
    }

    public SymbolTable getGlobalSymbols() {
        return globalSymbols;
    }
}
