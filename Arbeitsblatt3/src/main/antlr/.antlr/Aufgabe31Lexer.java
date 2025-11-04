// Generated from /home/jef/git/CompilerBau/Arbeitsblatt3/src/main/antlr/Aufgabe31.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Aufgabe31Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, ID=8, ASSIGN=9, 
		NUMBER=10, STRING=11, GREATERTHAN=12, LESSTHAN=13, PLUS=14, MINUS=15, 
		DIV=16, MUL=17, EQUAL=18, NEQUAL=19, COMMENT=20, WS=21, NEWLINE=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "ID", "ASSIGN", 
			"NUMBER", "STRING", "GREATERTHAN", "LESSTHAN", "PLUS", "MINUS", "DIV", 
			"MUL", "EQUAL", "NEQUAL", "COMMENT", "WS", "NEWLINE"
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


	public Aufgabe31Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Aufgabe31.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u008c\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\7\tN\n\t\f\t\16\tQ\13\t\3\n\3\n\3"+
		"\n\3\13\6\13W\n\13\r\13\16\13X\3\f\3\f\7\f]\n\f\f\f\16\f`\13\f\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25z\n\25\f\25\16\25}\13\25\3"+
		"\25\3\25\3\26\6\26\u0082\n\26\r\26\16\26\u0083\3\26\3\26\3\27\6\27\u0089"+
		"\n\27\r\27\16\27\u008a\2\2\30\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30\3\2\b"+
		"\3\2c|\5\2\62;C\\c|\3\2\62;\5\2\f\f\17\17$$\4\2\f\f\17\17\4\2\13\13\""+
		"\"\2\u0091\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\3/\3\2\2\2\5\65\3\2\2\2\78\3\2\2\2\t;\3\2\2\2\13C\3\2\2\2\rG\3\2\2\2"+
		"\17I\3\2\2\2\21K\3\2\2\2\23R\3\2\2\2\25V\3\2\2\2\27Z\3\2\2\2\31c\3\2\2"+
		"\2\33e\3\2\2\2\35g\3\2\2\2\37i\3\2\2\2!k\3\2\2\2#m\3\2\2\2%o\3\2\2\2\'"+
		"r\3\2\2\2)u\3\2\2\2+\u0081\3\2\2\2-\u0088\3\2\2\2/\60\7y\2\2\60\61\7j"+
		"\2\2\61\62\7k\2\2\62\63\7n\2\2\63\64\7g\2\2\64\4\3\2\2\2\65\66\7f\2\2"+
		"\66\67\7q\2\2\67\6\3\2\2\289\7k\2\29:\7h\2\2:\b\3\2\2\2;<\7g\2\2<=\7n"+
		"\2\2=>\7u\2\2>?\7g\2\2?@\7\"\2\2@A\7f\2\2AB\7q\2\2B\n\3\2\2\2CD\7g\2\2"+
		"DE\7p\2\2EF\7f\2\2F\f\3\2\2\2GH\7*\2\2H\16\3\2\2\2IJ\7+\2\2J\20\3\2\2"+
		"\2KO\t\2\2\2LN\t\3\2\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\22\3\2"+
		"\2\2QO\3\2\2\2RS\7<\2\2ST\7?\2\2T\24\3\2\2\2UW\t\4\2\2VU\3\2\2\2WX\3\2"+
		"\2\2XV\3\2\2\2XY\3\2\2\2Y\26\3\2\2\2Z^\7$\2\2[]\n\5\2\2\\[\3\2\2\2]`\3"+
		"\2\2\2^\\\3\2\2\2^_\3\2\2\2_a\3\2\2\2`^\3\2\2\2ab\7$\2\2b\30\3\2\2\2c"+
		"d\7@\2\2d\32\3\2\2\2ef\7>\2\2f\34\3\2\2\2gh\7-\2\2h\36\3\2\2\2ij\7/\2"+
		"\2j \3\2\2\2kl\7\61\2\2l\"\3\2\2\2mn\7,\2\2n$\3\2\2\2op\7?\2\2pq\7?\2"+
		"\2q&\3\2\2\2rs\7#\2\2st\7?\2\2t(\3\2\2\2uv\7\61\2\2vw\7\61\2\2w{\3\2\2"+
		"\2xz\n\6\2\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}{\3\2\2"+
		"\2~\177\b\25\2\2\177*\3\2\2\2\u0080\u0082\t\7\2\2\u0081\u0080\3\2\2\2"+
		"\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085"+
		"\3\2\2\2\u0085\u0086\b\26\2\2\u0086,\3\2\2\2\u0087\u0089\t\6\2\2\u0088"+
		"\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2"+
		"\2\2\u008b.\3\2\2\2\t\2OX^{\u0083\u008a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}