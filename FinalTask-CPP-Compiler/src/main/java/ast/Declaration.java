package ast;

import java.util.List;

public sealed interface Declaration extends ASTNode {

  record FunctionDef(Type returnType, String name, List<Parameter> parameters, Statement.Block body)
      implements Declaration {}

  record FunctionDecl(Type returnType, String name, List<Parameter> parameters)
      implements Declaration {}

  record ClassDef(
      String name,
      String baseClass, // null wenn keine Vererbung
      List<ClassMember.FieldDecl> fields,
      List<ClassMember.ConstructorDef> constructors,
      List<ClassMember.MethodDef> methods)
      implements Declaration {}

  record VarDecl(Type type, boolean isReference, String name) implements Declaration {}

  record VarDef(Type type, boolean isReference, String name, Expression initializer)
      implements Declaration {}
}
