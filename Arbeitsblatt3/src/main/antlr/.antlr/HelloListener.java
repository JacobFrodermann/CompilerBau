// Generated from c:/Users/xenia/Documents/GitHub/dbshit2/CompilerbauAB3/src/main/antlr/Hello.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(HelloParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(HelloParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(HelloParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(HelloParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(HelloParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(HelloParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(HelloParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(HelloParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(HelloParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(HelloParser.AtomContext ctx);
}