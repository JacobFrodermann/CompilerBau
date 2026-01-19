package ast;

import java.util.List;

public record ProgramNode(List<Declaration> declarations) implements ASTNode {}
