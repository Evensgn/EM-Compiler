// Generated from EMxStar.g4 by ANTLR 4.7.1

package com.evensgn.emcompiler.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EMxStarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, Bool=33, Int=34, String=35, Void=36, If=37, Else=38, For=39, 
		While=40, Break=41, Continue=42, Return=43, New=44, Class=45, IntegerConstant=46, 
		StringConst=47, NullLiteral=48, BoolConstant=49, Identifier=50, WhiteSpace=51, 
		NewLine=52, LineComment=53, BlockComment=54;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "Bool", 
		"Int", "String", "Null", "Void", "True", "False", "If", "Else", "For", 
		"While", "Break", "Continue", "Return", "New", "Class", "IntegerConstant", 
		"StringConst", "StringCharacter", "NullLiteral", "BoolConstant", "Identifier", 
		"IdentifierNonDigit", "Digit", "WhiteSpace", "NewLine", "LineComment", 
		"BlockComment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'{'", "'}'", "';'", "','", "'='", "'['", "']'", "'++'", 
		"'--'", "'.'", "'+'", "'-'", "'!'", "'~'", "'*'", "'/'", "'%'", "'<<'", 
		"'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&'", "'^'", "'|'", 
		"'&&'", "'||'", "'bool'", "'int'", "'string'", "'void'", "'if'", "'else'", 
		"'for'", "'while'", "'break'", "'continue'", "'return'", "'new'", "'class'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "Bool", "Int", "String", 
		"Void", "If", "Else", "For", "While", "Break", "Continue", "Return", "New", 
		"Class", "IntegerConstant", "StringConst", "NullLiteral", "BoolConstant", 
		"Identifier", "WhiteSpace", "NewLine", "LineComment", "BlockComment"
	};
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


	public EMxStarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "EMxStar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\28\u016d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3"+
		"\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3"+
		"\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3"+
		"\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&"+
		"\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3*\3*\3*\3*\3*\3+\3+\3"+
		"+\3+\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3.\3.\3"+
		"/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61"+
		"\3\62\3\62\7\62\u011f\n\62\f\62\16\62\u0122\13\62\3\62\5\62\u0125\n\62"+
		"\3\63\3\63\7\63\u0129\n\63\f\63\16\63\u012c\13\63\3\63\3\63\3\64\3\64"+
		"\3\64\5\64\u0133\n\64\3\65\3\65\3\66\3\66\5\66\u0139\n\66\3\67\3\67\3"+
		"\67\7\67\u013e\n\67\f\67\16\67\u0141\13\67\38\38\39\39\3:\6:\u0148\n:"+
		"\r:\16:\u0149\3:\3:\3;\5;\u014f\n;\3;\3;\3;\3;\3<\3<\3<\3<\7<\u0159\n"+
		"<\f<\16<\u015c\13<\3<\3<\3=\3=\3=\3=\7=\u0164\n=\f=\16=\u0167\13=\3=\3"+
		"=\3=\3=\3=\3\u0165\2>\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I\2K&M\2O\2Q\'S(U)W*Y+[,]-_.a/c\60e\61"+
		"g\2i\62k\63m\64o\2q\2s\65u\66w\67y8\3\2\t\3\2\63;\3\2\62;\6\2\f\f\17\17"+
		"$$^^\5\2$$^^pp\5\2C\\aac|\4\2\13\13\"\"\4\2\f\f\17\17\2\u0171\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2K\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2"+
		"\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2i\3\2\2\2\2"+
		"k\3\2\2\2\2m\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\3{\3"+
		"\2\2\2\5}\3\2\2\2\7\177\3\2\2\2\t\u0081\3\2\2\2\13\u0083\3\2\2\2\r\u0085"+
		"\3\2\2\2\17\u0087\3\2\2\2\21\u0089\3\2\2\2\23\u008b\3\2\2\2\25\u008d\3"+
		"\2\2\2\27\u0090\3\2\2\2\31\u0093\3\2\2\2\33\u0095\3\2\2\2\35\u0097\3\2"+
		"\2\2\37\u0099\3\2\2\2!\u009b\3\2\2\2#\u009d\3\2\2\2%\u009f\3\2\2\2\'\u00a1"+
		"\3\2\2\2)\u00a3\3\2\2\2+\u00a6\3\2\2\2-\u00a9\3\2\2\2/\u00ab\3\2\2\2\61"+
		"\u00ad\3\2\2\2\63\u00b0\3\2\2\2\65\u00b3\3\2\2\2\67\u00b6\3\2\2\29\u00b9"+
		"\3\2\2\2;\u00bb\3\2\2\2=\u00bd\3\2\2\2?\u00bf\3\2\2\2A\u00c2\3\2\2\2C"+
		"\u00c5\3\2\2\2E\u00ca\3\2\2\2G\u00ce\3\2\2\2I\u00d5\3\2\2\2K\u00da\3\2"+
		"\2\2M\u00df\3\2\2\2O\u00e4\3\2\2\2Q\u00ea\3\2\2\2S\u00ed\3\2\2\2U\u00f2"+
		"\3\2\2\2W\u00f6\3\2\2\2Y\u00fc\3\2\2\2[\u0102\3\2\2\2]\u010b\3\2\2\2_"+
		"\u0112\3\2\2\2a\u0116\3\2\2\2c\u0124\3\2\2\2e\u0126\3\2\2\2g\u0132\3\2"+
		"\2\2i\u0134\3\2\2\2k\u0138\3\2\2\2m\u013a\3\2\2\2o\u0142\3\2\2\2q\u0144"+
		"\3\2\2\2s\u0147\3\2\2\2u\u014e\3\2\2\2w\u0154\3\2\2\2y\u015f\3\2\2\2{"+
		"|\7*\2\2|\4\3\2\2\2}~\7+\2\2~\6\3\2\2\2\177\u0080\7}\2\2\u0080\b\3\2\2"+
		"\2\u0081\u0082\7\177\2\2\u0082\n\3\2\2\2\u0083\u0084\7=\2\2\u0084\f\3"+
		"\2\2\2\u0085\u0086\7.\2\2\u0086\16\3\2\2\2\u0087\u0088\7?\2\2\u0088\20"+
		"\3\2\2\2\u0089\u008a\7]\2\2\u008a\22\3\2\2\2\u008b\u008c\7_\2\2\u008c"+
		"\24\3\2\2\2\u008d\u008e\7-\2\2\u008e\u008f\7-\2\2\u008f\26\3\2\2\2\u0090"+
		"\u0091\7/\2\2\u0091\u0092\7/\2\2\u0092\30\3\2\2\2\u0093\u0094\7\60\2\2"+
		"\u0094\32\3\2\2\2\u0095\u0096\7-\2\2\u0096\34\3\2\2\2\u0097\u0098\7/\2"+
		"\2\u0098\36\3\2\2\2\u0099\u009a\7#\2\2\u009a \3\2\2\2\u009b\u009c\7\u0080"+
		"\2\2\u009c\"\3\2\2\2\u009d\u009e\7,\2\2\u009e$\3\2\2\2\u009f\u00a0\7\61"+
		"\2\2\u00a0&\3\2\2\2\u00a1\u00a2\7\'\2\2\u00a2(\3\2\2\2\u00a3\u00a4\7>"+
		"\2\2\u00a4\u00a5\7>\2\2\u00a5*\3\2\2\2\u00a6\u00a7\7@\2\2\u00a7\u00a8"+
		"\7@\2\2\u00a8,\3\2\2\2\u00a9\u00aa\7>\2\2\u00aa.\3\2\2\2\u00ab\u00ac\7"+
		"@\2\2\u00ac\60\3\2\2\2\u00ad\u00ae\7>\2\2\u00ae\u00af\7?\2\2\u00af\62"+
		"\3\2\2\2\u00b0\u00b1\7@\2\2\u00b1\u00b2\7?\2\2\u00b2\64\3\2\2\2\u00b3"+
		"\u00b4\7?\2\2\u00b4\u00b5\7?\2\2\u00b5\66\3\2\2\2\u00b6\u00b7\7#\2\2\u00b7"+
		"\u00b8\7?\2\2\u00b88\3\2\2\2\u00b9\u00ba\7(\2\2\u00ba:\3\2\2\2\u00bb\u00bc"+
		"\7`\2\2\u00bc<\3\2\2\2\u00bd\u00be\7~\2\2\u00be>\3\2\2\2\u00bf\u00c0\7"+
		"(\2\2\u00c0\u00c1\7(\2\2\u00c1@\3\2\2\2\u00c2\u00c3\7~\2\2\u00c3\u00c4"+
		"\7~\2\2\u00c4B\3\2\2\2\u00c5\u00c6\7d\2\2\u00c6\u00c7\7q\2\2\u00c7\u00c8"+
		"\7q\2\2\u00c8\u00c9\7n\2\2\u00c9D\3\2\2\2\u00ca\u00cb\7k\2\2\u00cb\u00cc"+
		"\7p\2\2\u00cc\u00cd\7v\2\2\u00cdF\3\2\2\2\u00ce\u00cf\7u\2\2\u00cf\u00d0"+
		"\7v\2\2\u00d0\u00d1\7t\2\2\u00d1\u00d2\7k\2\2\u00d2\u00d3\7p\2\2\u00d3"+
		"\u00d4\7i\2\2\u00d4H\3\2\2\2\u00d5\u00d6\7p\2\2\u00d6\u00d7\7w\2\2\u00d7"+
		"\u00d8\7n\2\2\u00d8\u00d9\7n\2\2\u00d9J\3\2\2\2\u00da\u00db\7x\2\2\u00db"+
		"\u00dc\7q\2\2\u00dc\u00dd\7k\2\2\u00dd\u00de\7f\2\2\u00deL\3\2\2\2\u00df"+
		"\u00e0\7v\2\2\u00e0\u00e1\7t\2\2\u00e1\u00e2\7w\2\2\u00e2\u00e3\7g\2\2"+
		"\u00e3N\3\2\2\2\u00e4\u00e5\7h\2\2\u00e5\u00e6\7c\2\2\u00e6\u00e7\7n\2"+
		"\2\u00e7\u00e8\7u\2\2\u00e8\u00e9\7g\2\2\u00e9P\3\2\2\2\u00ea\u00eb\7"+
		"k\2\2\u00eb\u00ec\7h\2\2\u00ecR\3\2\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef"+
		"\7n\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f1\7g\2\2\u00f1T\3\2\2\2\u00f2\u00f3"+
		"\7h\2\2\u00f3\u00f4\7q\2\2\u00f4\u00f5\7t\2\2\u00f5V\3\2\2\2\u00f6\u00f7"+
		"\7y\2\2\u00f7\u00f8\7j\2\2\u00f8\u00f9\7k\2\2\u00f9\u00fa\7n\2\2\u00fa"+
		"\u00fb\7g\2\2\u00fbX\3\2\2\2\u00fc\u00fd\7d\2\2\u00fd\u00fe\7t\2\2\u00fe"+
		"\u00ff\7g\2\2\u00ff\u0100\7c\2\2\u0100\u0101\7m\2\2\u0101Z\3\2\2\2\u0102"+
		"\u0103\7e\2\2\u0103\u0104\7q\2\2\u0104\u0105\7p\2\2\u0105\u0106\7v\2\2"+
		"\u0106\u0107\7k\2\2\u0107\u0108\7p\2\2\u0108\u0109\7w\2\2\u0109\u010a"+
		"\7g\2\2\u010a\\\3\2\2\2\u010b\u010c\7t\2\2\u010c\u010d\7g\2\2\u010d\u010e"+
		"\7v\2\2\u010e\u010f\7w\2\2\u010f\u0110\7t\2\2\u0110\u0111\7p\2\2\u0111"+
		"^\3\2\2\2\u0112\u0113\7p\2\2\u0113\u0114\7g\2\2\u0114\u0115\7y\2\2\u0115"+
		"`\3\2\2\2\u0116\u0117\7e\2\2\u0117\u0118\7n\2\2\u0118\u0119\7c\2\2\u0119"+
		"\u011a\7u\2\2\u011a\u011b\7u\2\2\u011bb\3\2\2\2\u011c\u0120\t\2\2\2\u011d"+
		"\u011f\t\3\2\2\u011e\u011d\3\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2"+
		"\2\2\u0120\u0121\3\2\2\2\u0121\u0125\3\2\2\2\u0122\u0120\3\2\2\2\u0123"+
		"\u0125\7\62\2\2\u0124\u011c\3\2\2\2\u0124\u0123\3\2\2\2\u0125d\3\2\2\2"+
		"\u0126\u012a\7$\2\2\u0127\u0129\5g\64\2\u0128\u0127\3\2\2\2\u0129\u012c"+
		"\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012d\3\2\2\2\u012c"+
		"\u012a\3\2\2\2\u012d\u012e\7$\2\2\u012ef\3\2\2\2\u012f\u0133\n\4\2\2\u0130"+
		"\u0131\7^\2\2\u0131\u0133\t\5\2\2\u0132\u012f\3\2\2\2\u0132\u0130\3\2"+
		"\2\2\u0133h\3\2\2\2\u0134\u0135\5I%\2\u0135j\3\2\2\2\u0136\u0139\5M\'"+
		"\2\u0137\u0139\5O(\2\u0138\u0136\3\2\2\2\u0138\u0137\3\2\2\2\u0139l\3"+
		"\2\2\2\u013a\u013f\5o8\2\u013b\u013e\5o8\2\u013c\u013e\5q9\2\u013d\u013b"+
		"\3\2\2\2\u013d\u013c\3\2\2\2\u013e\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f"+
		"\u0140\3\2\2\2\u0140n\3\2\2\2\u0141\u013f\3\2\2\2\u0142\u0143\t\6\2\2"+
		"\u0143p\3\2\2\2\u0144\u0145\t\3\2\2\u0145r\3\2\2\2\u0146\u0148\t\7\2\2"+
		"\u0147\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a"+
		"\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u014c\b:\2\2\u014ct\3\2\2\2\u014d\u014f"+
		"\7\17\2\2\u014e\u014d\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\3\2\2\2"+
		"\u0150\u0151\7\f\2\2\u0151\u0152\3\2\2\2\u0152\u0153\b;\2\2\u0153v\3\2"+
		"\2\2\u0154\u0155\7\61\2\2\u0155\u0156\7\61\2\2\u0156\u015a\3\2\2\2\u0157"+
		"\u0159\n\b\2\2\u0158\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u0158\3\2"+
		"\2\2\u015a\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u015a\3\2\2\2\u015d"+
		"\u015e\b<\2\2\u015ex\3\2\2\2\u015f\u0160\7\61\2\2\u0160\u0161\7,\2\2\u0161"+
		"\u0165\3\2\2\2\u0162\u0164\13\2\2\2\u0163\u0162\3\2\2\2\u0164\u0167\3"+
		"\2\2\2\u0165\u0166\3\2\2\2\u0165\u0163\3\2\2\2\u0166\u0168\3\2\2\2\u0167"+
		"\u0165\3\2\2\2\u0168\u0169\7,\2\2\u0169\u016a\7\61\2\2\u016a\u016b\3\2"+
		"\2\2\u016b\u016c\b=\2\2\u016cz\3\2\2\2\16\2\u0120\u0124\u012a\u0132\u0138"+
		"\u013d\u013f\u0149\u014e\u015a\u0165\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}