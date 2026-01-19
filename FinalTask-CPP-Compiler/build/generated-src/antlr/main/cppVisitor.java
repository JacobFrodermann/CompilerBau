// Generated from cpp.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link cppParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface cppVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link cppParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(cppParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#toplvl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToplvl(cppParser.ToplvlContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(cppParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(cppParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(cppParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(cppParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(cppParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(cppParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#functionCallStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallStmt(cppParser.FunctionCallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(cppParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(cppParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(cppParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(cppParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#fieldDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDecl(cppParser.FieldDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#constructorDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDef(cppParser.ConstructorDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#methodDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDef(cppParser.MethodDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(cppParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(cppParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(cppParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(cppParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(cppParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#logicalOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOr(cppParser.LogicalOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#logicalAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAnd(cppParser.LogicalAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(cppParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#memberAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccess(cppParser.MemberAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(cppParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#intLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(cppParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#boolLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(cppParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMath(cppParser.MathContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#multiplicative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative(cppParser.MultiplicativeContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(cppParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(cppParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#functionCallExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpr(cppParser.FunctionCallExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link cppParser#returnSTMT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnSTMT(cppParser.ReturnSTMTContext ctx);
}