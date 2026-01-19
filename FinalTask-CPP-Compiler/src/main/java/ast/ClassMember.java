package ast;

import java.util.List;

public sealed interface ClassMember extends ASTNode {

  record FieldDecl(Type type, String name) implements ClassMember {}

  record ConstructorDef(String name, List<Parameter> parameters, Statement.Block body)
      implements ClassMember {} // name = Klassenname

  record MethodDef(
      boolean isVirtual,
      Type returnType,
      String name,
      List<Parameter> parameters,
      Statement.Block body)
      implements ClassMember {}
}
