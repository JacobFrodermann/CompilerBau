package ast;

import java.util.List;

public sealed interface Expression extends ASTNode {

  // Literals
  record IntLiteral(int value) implements Expression {}

  record BoolLiteral(boolean value) implements Expression {}

  record CharLiteral(char value) implements Expression {}

  record StringLiteral(String value) implements Expression {}

  // Variables & Access
  record Variable(String name) implements Expression {}

  record MemberAccess(Expression object, String memberName)
      implements Expression {} // object meist Variable

  // Function Call als Expression
  record FunctionCall(String functionName, List<Expression> arguments) implements Expression {}

  record MethodCall(Expression object, String methodName, List<Expression> arguments)
      implements Expression {}

  // Binary Operations
  record BinaryOp(Expression left, String operator, Expression right)
      implements Expression {} // "+", "-", "*", "/", "%", "==", "!=", "<", ">", "<=", ">=", "&&",

  // "||"

  // Unary Operations
  record UnaryOp(String operator, Expression operand) implements Expression {} // "+", "-", "!"
}
