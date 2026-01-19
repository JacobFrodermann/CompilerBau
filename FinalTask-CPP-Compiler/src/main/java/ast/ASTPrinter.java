package ast;

public class ASTPrinter {

  private final StringBuilder sb = new StringBuilder();
  private int indent = 0;

  public String print(ASTNode node) {
    sb.setLength(0);
    indent = 0;
    printNode(node);
    return sb.toString();
  }

  private void printNode(ASTNode node) {
    switch (node) {
      case ProgramNode p -> printProgram(p);
      case Declaration d -> printDeclaration(d);
      case Statement s -> printStatement(s);
      case Expression e -> printExpression(e);
      case ClassMember m -> printClassMember(m);
      default -> line("Unknown: " + node.getClass().getSimpleName());
    }
  }

  private void printProgram(ProgramNode p) {
    line("Program");
    indent++;
    for (var decl : p.declarations()) {
      printNode(decl);
    }
    indent--;
  }

  private void printDeclaration(Declaration d) {
    switch (d) {
      case Declaration.FunctionDef f -> {
        line("FunctionDef: " + f.returnType().name() + " " + f.name());
        indent++;
        if (!f.parameters().isEmpty()) {
          line("Parameters:");
          indent++;
          for (var param : f.parameters()) {
            printParameter(param);
          }
          indent--;
        }
        line("Body:");
        indent++;
        printNode(f.body());
        indent--;
        indent--;
      }
      case Declaration.FunctionDecl f -> {
        line("FunctionDecl: " + f.returnType().name() + " " + f.name());
        indent++;
        if (!f.parameters().isEmpty()) {
          line("Parameters:");
          indent++;
          for (var param : f.parameters()) {
            printParameter(param);
          }
          indent--;
        }
        indent--;
      }
      case Declaration.ClassDef c -> {
        String ext = c.baseClass() != null ? " extends " + c.baseClass() : "";
        line("ClassDef: " + c.name() + ext);
        indent++;
        if (!c.fields().isEmpty()) {
          line("Fields:");
          indent++;
          for (var field : c.fields()) {
            printNode(field);
          }
          indent--;
        }
        if (!c.constructors().isEmpty()) {
          line("Constructors:");
          indent++;
          for (var ctor : c.constructors()) {
            printNode(ctor);
          }
          indent--;
        }
        if (!c.methods().isEmpty()) {
          line("Methods:");
          indent++;
          for (var method : c.methods()) {
            printNode(method);
          }
          indent--;
        }
        indent--;
      }
      case Declaration.VarDecl v -> {
        String ref = v.isReference() ? "&" : "";
        line("VarDecl: " + v.type().name() + ref + " " + v.name());
      }
      case Declaration.VarDef v -> {
        String ref = v.isReference() ? "&" : "";
        line("VarDef: " + v.type().name() + ref + " " + v.name() + " =");
        indent++;
        printNode(v.initializer());
        indent--;
      }
    }
  }

  private void printStatement(Statement s) {
    switch (s) {
      case Statement.Block b -> {
        line("Block");
        indent++;
        for (var stmt : b.statements()) {
          printNode(stmt);
        }
        indent--;
      }
      case Statement.VarDecl v -> {
        String ref = v.isReference() ? "&" : "";
        line("VarDecl: " + v.type().name() + ref + " " + v.name());
      }
      case Statement.VarDef v -> {
        String ref = v.isReference() ? "&" : "";
        line("VarDef: " + v.type().name() + ref + " " + v.name() + " =");
        indent++;
        printNode(v.initializer());
        indent--;
      }
      case Statement.Assignment a -> {
        line("Assignment");
        indent++;
        line("Target:");
        indent++;
        printNode(a.target());
        indent--;
        line("Value:");
        indent++;
        printNode(a.value());
        indent--;
        indent--;
      }
      case Statement.FunctionCall f -> {
        line("FunctionCall: " + f.functionName());
        if (!f.arguments().isEmpty()) {
          indent++;
          line("Arguments:");
          indent++;
          for (var arg : f.arguments()) {
            printNode(arg);
          }
          indent--;
          indent--;
        }
      }
      case Statement.If i -> {
        line("If");
        indent++;
        line("Condition:");
        indent++;
        printNode(i.condition());
        indent--;
        line("Then:");
        indent++;
        printNode(i.thenBranch());
        indent--;
        if (i.elseBranch() != null) {
          line("Else:");
          indent++;
          printNode(i.elseBranch());
          indent--;
        }
        indent--;
      }
      case Statement.While w -> {
        line("While");
        indent++;
        line("Condition:");
        indent++;
        printNode(w.condition());
        indent--;
        line("Body:");
        indent++;
        printNode(w.body());
        indent--;
        indent--;
      }
      case Statement.Return r -> {
        if (r.value() != null) {
          line("Return");
          indent++;
          printNode(r.value());
          indent--;
        } else {
          line("Return (void)");
        }
      }
      case Statement.Empty e -> line("Empty");
    }
  }

  private void printExpression(Expression e) {
    switch (e) {
      case Expression.IntLiteral i -> line("Int: " + i.value());
      case Expression.BoolLiteral b -> line("Bool: " + b.value());
      case Expression.StringLiteral s -> line("String: \"" + s.value() + "\"");
      case Expression.Variable v -> line("Var: " + v.name());
      case Expression.MemberAccess m -> {
        line("MemberAccess: ." + m.memberName());
        indent++;
        printNode(m.object());
        indent--;
      }
      case Expression.FunctionCall f -> {
        line("FunctionCall: " + f.functionName());
        if (!f.arguments().isEmpty()) {
          indent++;
          line("Arguments:");
          indent++;
          for (var arg : f.arguments()) {
            printNode(arg);
          }
          indent--;
          indent--;
        }
      }
      case Expression.BinaryOp b -> {
        line("BinaryOp: " + b.operator());
        indent++;
        printNode(b.left());
        printNode(b.right());
        indent--;
      }
      case Expression.UnaryOp u -> {
        line("UnaryOp: " + u.operator());
        indent++;
        printNode(u.operand());
        indent--;
      }
    }
  }

  private void printClassMember(ClassMember m) {
    switch (m) {
      case ClassMember.FieldDecl f -> line("Field: " + f.type().name() + " " + f.name());
      case ClassMember.ConstructorDef c -> {
        line("Constructor: " + c.name());
        indent++;
        if (!c.parameters().isEmpty()) {
          line("Parameters:");
          indent++;
          for (var param : c.parameters()) {
            printParameter(param);
          }
          indent--;
        }
        line("Body:");
        indent++;
        printNode(c.body());
        indent--;
        indent--;
      }
      case ClassMember.MethodDef m2 -> {
        String virt = m2.isVirtual() ? "virtual " : "";
        line("Method: " + virt + m2.returnType().name() + " " + m2.name());
        indent++;
        if (!m2.parameters().isEmpty()) {
          line("Parameters:");
          indent++;
          for (var param : m2.parameters()) {
            printParameter(param);
          }
          indent--;
        }
        line("Body:");
        indent++;
        printNode(m2.body());
        indent--;
        indent--;
      }
    }
  }

  private void printParameter(Parameter p) {
    String ref = p.isReference() ? "&" : "";
    line("Param: " + p.type().name() + ref + " " + p.name());
  }

  private void line(String text) {
    sb.append("  ".repeat(indent)).append(text).append("\n");
  }
}
