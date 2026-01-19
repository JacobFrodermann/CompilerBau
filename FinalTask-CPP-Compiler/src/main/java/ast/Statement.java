package ast;

import java.util.List;

public sealed interface Statement extends ASTNode {

  record Block(List<Statement> statements) implements Statement {}

  record VarDecl(Type type, boolean isReference, String name) implements Statement {}

  record VarDef(Type type, boolean isReference, String name, Expression initializer)
      implements Statement {}

  record Assignment(Expression target, Expression value)
      implements Statement {} // target ist LValue: Variable oder MemberAccess

  record FunctionCall(String functionName, List<Expression> arguments) implements Statement {}

  record If(Expression condition, Statement thenBranch, Statement elseBranch)
      implements Statement {} // elseBranch null wenn kein else

  record While(Expression condition, Statement body) implements Statement {}

  record Return(Expression value) implements Statement {} // value null bei void return

  record Empty() implements Statement {} // fuer ';' halt...
}
