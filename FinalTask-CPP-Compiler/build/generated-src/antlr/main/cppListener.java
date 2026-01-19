// Generated from cpp.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link cppParser}.
 */
public interface cppListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link cppParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(cppParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(cppParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#toplvl}.
	 * @param ctx the parse tree
	 */
	void enterToplvl(cppParser.ToplvlContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#toplvl}.
	 * @param ctx the parse tree
	 */
	void exitToplvl(cppParser.ToplvlContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(cppParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(cppParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(cppParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(cppParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(cppParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(cppParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(cppParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(cppParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(cppParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(cppParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(cppParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(cppParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#functionCallStmt}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallStmt(cppParser.FunctionCallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#functionCallStmt}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallStmt(cppParser.FunctionCallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(cppParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(cppParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(cppParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(cppParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(cppParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(cppParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(cppParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(cppParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#fieldDecl}.
	 * @param ctx the parse tree
	 */
	void enterFieldDecl(cppParser.FieldDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#fieldDecl}.
	 * @param ctx the parse tree
	 */
	void exitFieldDecl(cppParser.FieldDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#constructorDef}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDef(cppParser.ConstructorDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#constructorDef}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDef(cppParser.ConstructorDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#methodDef}.
	 * @param ctx the parse tree
	 */
	void enterMethodDef(cppParser.MethodDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#methodDef}.
	 * @param ctx the parse tree
	 */
	void exitMethodDef(cppParser.MethodDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(cppParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(cppParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(cppParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(cppParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(cppParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(cppParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(cppParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(cppParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(cppParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(cppParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#logicalOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOr(cppParser.LogicalOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#logicalOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOr(cppParser.LogicalOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#logicalAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAnd(cppParser.LogicalAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#logicalAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAnd(cppParser.LogicalAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(cppParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(cppParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(cppParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(cppParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(cppParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(cppParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#intLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(cppParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#intLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(cppParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(cppParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(cppParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#math}.
	 * @param ctx the parse tree
	 */
	void enterMath(cppParser.MathContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#math}.
	 * @param ctx the parse tree
	 */
	void exitMath(cppParser.MathContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#multiplicative}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative(cppParser.MultiplicativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#multiplicative}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative(cppParser.MultiplicativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(cppParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(cppParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(cppParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(cppParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#functionCallExpr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpr(cppParser.FunctionCallExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#functionCallExpr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpr(cppParser.FunctionCallExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link cppParser#returnSTMT}.
	 * @param ctx the parse tree
	 */
	void enterReturnSTMT(cppParser.ReturnSTMTContext ctx);
	/**
	 * Exit a parse tree produced by {@link cppParser#returnSTMT}.
	 * @param ctx the parse tree
	 */
	void exitReturnSTMT(cppParser.ReturnSTMTContext ctx);
}