// Generated from /home/jef/git/CompilerBau/Arbeitsblatt3/src/main/antlr/Aufgabe31.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Aufgabe31Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, ID=8, ASSIGN=9, 
		NUMBER=10, STRING=11, GREATERTHAN=12, LESSTHAN=13, PLUS=14, MINUS=15, 
		DIV=16, MUL=17, EQUAL=18, NEQUAL=19, COMMENT=20, WS=21, NEWLINE=22;
	public static final int
		RULE_program = 0, RULE_loop = 1, RULE_condition = 2, RULE_vardec = 3, 
		RULE_assign = 4, RULE_expr = 5, RULE_comparison = 6, RULE_addition = 7, 
		RULE_multiplication = 8, RULE_idOrNum = 9, RULE_statement = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "loop", "condition", "vardec", "assign", "expr", "comparison", 
			"addition", "multiplication", "idOrNum", "statement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'while'", "'do'", "'if'", "'else do'", "'end'", "'('", "')'", 
			null, "':='", null, null, "'>'", "'<'", "'+'", "'-'", "'/'", "'*'", "'=='", 
			"'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "ID", "ASSIGN", "NUMBER", 
			"STRING", "GREATERTHAN", "LESSTHAN", "PLUS", "MINUS", "DIV", "MUL", "EQUAL", 
			"NEQUAL", "COMMENT", "WS", "NEWLINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Aufgabe31.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Aufgabe31Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Aufgabe31Parser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__5) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0)) {
				{
				{
				setState(22);
				statement();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_loop);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__0);
			setState(31);
			expr();
			setState(32);
			match(T__1);
			setState(34); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(33);
					statement();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(36); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(T__2);
			setState(39);
			expr();
			setState(40);
			match(T__1);
			setState(42); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(41);
				statement();
				}
				}
				setState(44); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__5) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0) );
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(46);
				match(T__3);
				setState(48); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(47);
					statement();
					}
					}
					setState(50); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__5) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0) );
				}
			}

			setState(54);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VardecContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Aufgabe31Parser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(Aufgabe31Parser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VardecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vardec; }
	}

	public final VardecContext vardec() throws RecognitionException {
		VardecContext _localctx = new VardecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_vardec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(ID);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(57);
				match(ASSIGN);
				setState(58);
				expr();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Aufgabe31Parser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(Aufgabe31Parser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(ID);
			setState(62);
			match(ASSIGN);
			setState(63);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ComparisonContext comparison() {
			return getRuleContext(ComparisonContext.class,0);
		}
		public TerminalNode STRING() { return getToken(Aufgabe31Parser.STRING, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expr);
		try {
			setState(67);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
			case ID:
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				comparison();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonContext extends ParserRuleContext {
		public List<AdditionContext> addition() {
			return getRuleContexts(AdditionContext.class);
		}
		public AdditionContext addition(int i) {
			return getRuleContext(AdditionContext.class,i);
		}
		public List<TerminalNode> EQUAL() { return getTokens(Aufgabe31Parser.EQUAL); }
		public TerminalNode EQUAL(int i) {
			return getToken(Aufgabe31Parser.EQUAL, i);
		}
		public List<TerminalNode> NEQUAL() { return getTokens(Aufgabe31Parser.NEQUAL); }
		public TerminalNode NEQUAL(int i) {
			return getToken(Aufgabe31Parser.NEQUAL, i);
		}
		public List<TerminalNode> LESSTHAN() { return getTokens(Aufgabe31Parser.LESSTHAN); }
		public TerminalNode LESSTHAN(int i) {
			return getToken(Aufgabe31Parser.LESSTHAN, i);
		}
		public List<TerminalNode> GREATERTHAN() { return getTokens(Aufgabe31Parser.GREATERTHAN); }
		public TerminalNode GREATERTHAN(int i) {
			return getToken(Aufgabe31Parser.GREATERTHAN, i);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			addition();
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GREATERTHAN) | (1L << LESSTHAN) | (1L << EQUAL) | (1L << NEQUAL))) != 0)) {
				{
				{
				setState(70);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GREATERTHAN) | (1L << LESSTHAN) | (1L << EQUAL) | (1L << NEQUAL))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(71);
				addition();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditionContext extends ParserRuleContext {
		public List<MultiplicationContext> multiplication() {
			return getRuleContexts(MultiplicationContext.class);
		}
		public MultiplicationContext multiplication(int i) {
			return getRuleContext(MultiplicationContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(Aufgabe31Parser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(Aufgabe31Parser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(Aufgabe31Parser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(Aufgabe31Parser.MINUS, i);
		}
		public AdditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addition; }
	}

	public final AdditionContext addition() throws RecognitionException {
		AdditionContext _localctx = new AdditionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_addition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			multiplication();
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(78);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(79);
				multiplication();
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicationContext extends ParserRuleContext {
		public List<IdOrNumContext> idOrNum() {
			return getRuleContexts(IdOrNumContext.class);
		}
		public IdOrNumContext idOrNum(int i) {
			return getRuleContext(IdOrNumContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(Aufgabe31Parser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(Aufgabe31Parser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(Aufgabe31Parser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(Aufgabe31Parser.DIV, i);
		}
		public MultiplicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplication; }
	}

	public final MultiplicationContext multiplication() throws RecognitionException {
		MultiplicationContext _localctx = new MultiplicationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_multiplication);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			idOrNum();
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DIV || _la==MUL) {
				{
				{
				setState(86);
				_la = _input.LA(1);
				if ( !(_la==DIV || _la==MUL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(87);
				idOrNum();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdOrNumContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Aufgabe31Parser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(Aufgabe31Parser.NUMBER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdOrNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOrNum; }
	}

	public final IdOrNumContext idOrNum() throws RecognitionException {
		IdOrNumContext _localctx = new IdOrNumContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_idOrNum);
		try {
			setState(99);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				match(ID);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				match(NUMBER);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(95);
				match(T__5);
				setState(96);
				expr();
				setState(97);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public VardecContext vardec() {
			return getRuleContext(VardecContext.class,0);
		}
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				vardec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				assign();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				loop();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(105);
				condition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30o\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\7\2\32\n\2\f\2\16\2\35\13\2\3\2\3\2\3\3\3\3\3\3\3\3\6\3%\n"+
		"\3\r\3\16\3&\3\4\3\4\3\4\3\4\6\4-\n\4\r\4\16\4.\3\4\3\4\6\4\63\n\4\r\4"+
		"\16\4\64\5\4\67\n\4\3\4\3\4\3\5\3\5\3\5\5\5>\n\5\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\5\7F\n\7\3\b\3\b\3\b\7\bK\n\b\f\b\16\bN\13\b\3\t\3\t\3\t\7\tS\n\t\f"+
		"\t\16\tV\13\t\3\n\3\n\3\n\7\n[\n\n\f\n\16\n^\13\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\5\13f\n\13\3\f\3\f\3\f\3\f\3\f\5\fm\n\f\3\f\2\2\r\2\4\6\b\n"+
		"\f\16\20\22\24\26\2\5\4\2\16\17\24\25\3\2\20\21\3\2\22\23\2s\2\33\3\2"+
		"\2\2\4 \3\2\2\2\6(\3\2\2\2\b:\3\2\2\2\n?\3\2\2\2\fE\3\2\2\2\16G\3\2\2"+
		"\2\20O\3\2\2\2\22W\3\2\2\2\24e\3\2\2\2\26l\3\2\2\2\30\32\5\26\f\2\31\30"+
		"\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\33\34\3\2\2\2\34\36\3\2\2\2\35\33"+
		"\3\2\2\2\36\37\7\2\2\3\37\3\3\2\2\2 !\7\3\2\2!\"\5\f\7\2\"$\7\4\2\2#%"+
		"\5\26\f\2$#\3\2\2\2%&\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'\5\3\2\2\2()\7\5\2"+
		"\2)*\5\f\7\2*,\7\4\2\2+-\5\26\f\2,+\3\2\2\2-.\3\2\2\2.,\3\2\2\2./\3\2"+
		"\2\2/\66\3\2\2\2\60\62\7\6\2\2\61\63\5\26\f\2\62\61\3\2\2\2\63\64\3\2"+
		"\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\60\3\2\2\2\66\67\3\2"+
		"\2\2\678\3\2\2\289\7\7\2\29\7\3\2\2\2:=\7\n\2\2;<\7\13\2\2<>\5\f\7\2="+
		";\3\2\2\2=>\3\2\2\2>\t\3\2\2\2?@\7\n\2\2@A\7\13\2\2AB\5\f\7\2B\13\3\2"+
		"\2\2CF\5\16\b\2DF\7\r\2\2EC\3\2\2\2ED\3\2\2\2F\r\3\2\2\2GL\5\20\t\2HI"+
		"\t\2\2\2IK\5\20\t\2JH\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\17\3\2\2"+
		"\2NL\3\2\2\2OT\5\22\n\2PQ\t\3\2\2QS\5\22\n\2RP\3\2\2\2SV\3\2\2\2TR\3\2"+
		"\2\2TU\3\2\2\2U\21\3\2\2\2VT\3\2\2\2W\\\5\24\13\2XY\t\4\2\2Y[\5\24\13"+
		"\2ZX\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]\23\3\2\2\2^\\\3\2\2\2_f"+
		"\7\n\2\2`f\7\f\2\2ab\7\b\2\2bc\5\f\7\2cd\7\t\2\2df\3\2\2\2e_\3\2\2\2e"+
		"`\3\2\2\2ea\3\2\2\2f\25\3\2\2\2gm\5\b\5\2hm\5\n\6\2im\5\f\7\2jm\5\4\3"+
		"\2km\5\6\4\2lg\3\2\2\2lh\3\2\2\2li\3\2\2\2lj\3\2\2\2lk\3\2\2\2m\27\3\2"+
		"\2\2\16\33&.\64\66=ELT\\el";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}