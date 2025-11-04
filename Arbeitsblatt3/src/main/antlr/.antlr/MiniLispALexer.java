// Generated from c:/Users/xenia/Documents/GitHub/dbshit2/CompilerbauAB3/src/main/antlr/MiniLispA.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MiniLispALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, TRUE=3, FALSE=4, ID=5, NUMBER=6, OP=7, STRING=8, COMMENT=9, 
		WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "TRUE", "FALSE", "ID", "NUMBER", "OP", "STRING", "COMMENT", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "TRUE", "FALSE", "ID", "NUMBER", "OP", "STRING", "COMMENT", 
			"WS"
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


	public MiniLispALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniLispA.g4"; }

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
		"\u0004\u0000\nM\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0005\u0004\'\b\u0004\n\u0004\f\u0004*"+
		"\t\u0004\u0001\u0005\u0004\u0005-\b\u0005\u000b\u0005\f\u0005.\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0005\u00075\b\u0007\n\u0007\f\u0007"+
		"8\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b@\b\b\n\b\f\bC\t\b\u0001\b\u0001\b\u0001\t\u0004\tH\b\t\u000b\t\f\t"+
		"I\u0001\t\u0001\t\u0000\u0000\n\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0001\u0000"+
		"\u0007\u0001\u0000az\u0003\u000009AZaz\u0001\u000009\u0004\u0000*+--/"+
		"/<>\u0003\u0000\n\n\r\r\"\"\u0002\u0000\n\n\r\r\u0004\u0000\t\n\r\r  "+
		",,Q\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0001"+
		"\u0015\u0001\u0000\u0000\u0000\u0003\u0017\u0001\u0000\u0000\u0000\u0005"+
		"\u0019\u0001\u0000\u0000\u0000\u0007\u001e\u0001\u0000\u0000\u0000\t$"+
		"\u0001\u0000\u0000\u0000\u000b,\u0001\u0000\u0000\u0000\r0\u0001\u0000"+
		"\u0000\u0000\u000f2\u0001\u0000\u0000\u0000\u0011;\u0001\u0000\u0000\u0000"+
		"\u0013G\u0001\u0000\u0000\u0000\u0015\u0016\u0005(\u0000\u0000\u0016\u0002"+
		"\u0001\u0000\u0000\u0000\u0017\u0018\u0005)\u0000\u0000\u0018\u0004\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0005t\u0000\u0000\u001a\u001b\u0005r\u0000"+
		"\u0000\u001b\u001c\u0005u\u0000\u0000\u001c\u001d\u0005e\u0000\u0000\u001d"+
		"\u0006\u0001\u0000\u0000\u0000\u001e\u001f\u0005f\u0000\u0000\u001f \u0005"+
		"a\u0000\u0000 !\u0005l\u0000\u0000!\"\u0005s\u0000\u0000\"#\u0005e\u0000"+
		"\u0000#\b\u0001\u0000\u0000\u0000$(\u0007\u0000\u0000\u0000%\'\u0007\u0001"+
		"\u0000\u0000&%\u0001\u0000\u0000\u0000\'*\u0001\u0000\u0000\u0000(&\u0001"+
		"\u0000\u0000\u0000()\u0001\u0000\u0000\u0000)\n\u0001\u0000\u0000\u0000"+
		"*(\u0001\u0000\u0000\u0000+-\u0007\u0002\u0000\u0000,+\u0001\u0000\u0000"+
		"\u0000-.\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000"+
		"\u0000\u0000/\f\u0001\u0000\u0000\u000001\u0007\u0003\u0000\u00001\u000e"+
		"\u0001\u0000\u0000\u000026\u0005\"\u0000\u000035\b\u0004\u0000\u00004"+
		"3\u0001\u0000\u0000\u000058\u0001\u0000\u0000\u000064\u0001\u0000\u0000"+
		"\u000067\u0001\u0000\u0000\u000079\u0001\u0000\u0000\u000086\u0001\u0000"+
		"\u0000\u00009:\u0005\"\u0000\u0000:\u0010\u0001\u0000\u0000\u0000;<\u0005"+
		";\u0000\u0000<=\u0005;\u0000\u0000=A\u0001\u0000\u0000\u0000>@\b\u0005"+
		"\u0000\u0000?>\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000\u0000A?\u0001"+
		"\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BD\u0001\u0000\u0000\u0000"+
		"CA\u0001\u0000\u0000\u0000DE\u0006\b\u0000\u0000E\u0012\u0001\u0000\u0000"+
		"\u0000FH\u0007\u0006\u0000\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000"+
		"\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000JK\u0001"+
		"\u0000\u0000\u0000KL\u0006\t\u0000\u0000L\u0014\u0001\u0000\u0000\u0000"+
		"\u0006\u0000(.6AI\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}