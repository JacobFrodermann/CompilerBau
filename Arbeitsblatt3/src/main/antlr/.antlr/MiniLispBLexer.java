// Generated from c:/Users/xenia/Documents/GitHub/dbshit2/CompilerbauAB3/src/main/antlr/MiniLispB.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MiniLispBLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, TRUE=7, FALSE=8, ID=9, 
		NUMBER=10, OP=11, STRING=12, COMMENT=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "TRUE", "FALSE", "ID", 
			"NUMBER", "OP", "STRING", "COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'list'", "')'", "'def'", "'defn'", "'let'", "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "TRUE", "FALSE", "ID", "NUMBER", 
			"OP", "STRING", "COMMENT", "WS"
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


	public MiniLispBLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniLispB.g4"; }

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
		"\u0004\u0000\u000eg\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0005\bA\b\b\n\b\f\bD\t\b\u0001\t\u0004\tG\b\t"+
		"\u000b\t\f\tH\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0005\u000bO\b\u000b"+
		"\n\u000b\f\u000bR\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0005\fZ\b\f\n\f\f\f]\t\f\u0001\f\u0001\f\u0001\r\u0004\rb"+
		"\b\r\u000b\r\f\rc\u0001\r\u0001\r\u0000\u0000\u000e\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u0001\u0000\u0007\u0001"+
		"\u0000az\u0003\u000009AZaz\u0001\u000009\u0004\u0000*+--//<>\u0003\u0000"+
		"\n\n\r\r\"\"\u0002\u0000\n\n\r\r\u0004\u0000\t\n\r\r  ,,k\u0000\u0001"+
		"\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001"+
		"\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000"+
		"\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000"+
		"\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000"+
		"\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000"+
		"\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0001\u001d\u0001\u0000"+
		"\u0000\u0000\u0003\u001f\u0001\u0000\u0000\u0000\u0005$\u0001\u0000\u0000"+
		"\u0000\u0007&\u0001\u0000\u0000\u0000\t*\u0001\u0000\u0000\u0000\u000b"+
		"/\u0001\u0000\u0000\u0000\r3\u0001\u0000\u0000\u0000\u000f8\u0001\u0000"+
		"\u0000\u0000\u0011>\u0001\u0000\u0000\u0000\u0013F\u0001\u0000\u0000\u0000"+
		"\u0015J\u0001\u0000\u0000\u0000\u0017L\u0001\u0000\u0000\u0000\u0019U"+
		"\u0001\u0000\u0000\u0000\u001ba\u0001\u0000\u0000\u0000\u001d\u001e\u0005"+
		"(\u0000\u0000\u001e\u0002\u0001\u0000\u0000\u0000\u001f \u0005l\u0000"+
		"\u0000 !\u0005i\u0000\u0000!\"\u0005s\u0000\u0000\"#\u0005t\u0000\u0000"+
		"#\u0004\u0001\u0000\u0000\u0000$%\u0005)\u0000\u0000%\u0006\u0001\u0000"+
		"\u0000\u0000&\'\u0005d\u0000\u0000\'(\u0005e\u0000\u0000()\u0005f\u0000"+
		"\u0000)\b\u0001\u0000\u0000\u0000*+\u0005d\u0000\u0000+,\u0005e\u0000"+
		"\u0000,-\u0005f\u0000\u0000-.\u0005n\u0000\u0000.\n\u0001\u0000\u0000"+
		"\u0000/0\u0005l\u0000\u000001\u0005e\u0000\u000012\u0005t\u0000\u0000"+
		"2\f\u0001\u0000\u0000\u000034\u0005t\u0000\u000045\u0005r\u0000\u0000"+
		"56\u0005u\u0000\u000067\u0005e\u0000\u00007\u000e\u0001\u0000\u0000\u0000"+
		"89\u0005f\u0000\u00009:\u0005a\u0000\u0000:;\u0005l\u0000\u0000;<\u0005"+
		"s\u0000\u0000<=\u0005e\u0000\u0000=\u0010\u0001\u0000\u0000\u0000>B\u0007"+
		"\u0000\u0000\u0000?A\u0007\u0001\u0000\u0000@?\u0001\u0000\u0000\u0000"+
		"AD\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000"+
		"\u0000C\u0012\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000EG\u0007"+
		"\u0002\u0000\u0000FE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000I\u0014\u0001\u0000"+
		"\u0000\u0000JK\u0007\u0003\u0000\u0000K\u0016\u0001\u0000\u0000\u0000"+
		"LP\u0005\"\u0000\u0000MO\b\u0004\u0000\u0000NM\u0001\u0000\u0000\u0000"+
		"OR\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000"+
		"\u0000QS\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000ST\u0005\"\u0000"+
		"\u0000T\u0018\u0001\u0000\u0000\u0000UV\u0005;\u0000\u0000VW\u0005;\u0000"+
		"\u0000W[\u0001\u0000\u0000\u0000XZ\b\u0005\u0000\u0000YX\u0001\u0000\u0000"+
		"\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000"+
		"\u0000\u0000\\^\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^_\u0006"+
		"\f\u0000\u0000_\u001a\u0001\u0000\u0000\u0000`b\u0007\u0006\u0000\u0000"+
		"a`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000"+
		"\u0000cd\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000ef\u0006\r\u0000"+
		"\u0000f\u001c\u0001\u0000\u0000\u0000\u0006\u0000BHP[c\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}