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
		While=40, Break=41, Continue=42, Return=43, New=44, Class=45, This=46, 
		IntegerConstant=47, StringConst=48, NullLiteral=49, BoolConstant=50, Identifier=51, 
		WhiteSpace=52, NewLine=53, LineComment=54, BlockComment=55;
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
		"While", "Break", "Continue", "Return", "New", "Class", "This", "IntegerConstant", 
		"StringConst", "StringCharacter", "NullLiteral", "BoolConstant", "Identifier", 
		"IdentifierNonDigitUnderline", "IdentifierNonDigit", "Digit", "WhiteSpace", 
		"NewLine", "LineComment", "BlockComment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'{'", "'}'", "';'", "','", "'='", "'['", "']'", "'++'", 
		"'--'", "'.'", "'+'", "'-'", "'!'", "'~'", "'*'", "'/'", "'%'", "'<<'", 
		"'>>'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&'", "'^'", "'|'", 
		"'&&'", "'||'", "'bool'", "'int'", "'string'", "'void'", "'if'", "'else'", 
		"'for'", "'while'", "'break'", "'continue'", "'return'", "'new'", "'class'", 
		"'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "Bool", "Int", "String", 
		"Void", "If", "Else", "For", "While", "Break", "Continue", "Return", "New", 
		"Class", "This", "IntegerConstant", "StringConst", "NullLiteral", "BoolConstant", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\29\u0178\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3"+
		"\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&"+
		"\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3*\3*\3*\3"+
		"*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3"+
		".\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61"+
		"\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\63\3\63\7\63\u0128\n\63\f\63\16"+
		"\63\u012b\13\63\3\63\5\63\u012e\n\63\3\64\3\64\7\64\u0132\n\64\f\64\16"+
		"\64\u0135\13\64\3\64\3\64\3\65\3\65\3\65\5\65\u013c\n\65\3\66\3\66\3\67"+
		"\3\67\5\67\u0142\n\67\38\38\38\78\u0147\n8\f8\168\u014a\138\39\39\3:\3"+
		":\3;\3;\3<\6<\u0153\n<\r<\16<\u0154\3<\3<\3=\5=\u015a\n=\3=\3=\3=\3=\3"+
		">\3>\3>\3>\7>\u0164\n>\f>\16>\u0167\13>\3>\3>\3?\3?\3?\3?\7?\u016f\n?"+
		"\f?\16?\u0172\13?\3?\3?\3?\3?\3?\3\u0170\2@\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I\2K&M\2O\2Q\'"+
		"S(U)W*Y+[,]-_.a/c\60e\61g\62i\2k\63m\64o\65q\2s\2u\2w\66y\67{8}9\3\2\n"+
		"\3\2\63;\3\2\62;\6\2\f\f\17\17$$^^\5\2$$^^pp\4\2C\\c|\5\2C\\aac|\4\2\13"+
		"\13\"\"\4\2\f\f\17\17\2\u017b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2K\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"+
		"\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2"+
		"\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2"+
		"w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\3\177\3\2\2\2\5\u0081\3\2\2"+
		"\2\7\u0083\3\2\2\2\t\u0085\3\2\2\2\13\u0087\3\2\2\2\r\u0089\3\2\2\2\17"+
		"\u008b\3\2\2\2\21\u008d\3\2\2\2\23\u008f\3\2\2\2\25\u0091\3\2\2\2\27\u0094"+
		"\3\2\2\2\31\u0097\3\2\2\2\33\u0099\3\2\2\2\35\u009b\3\2\2\2\37\u009d\3"+
		"\2\2\2!\u009f\3\2\2\2#\u00a1\3\2\2\2%\u00a3\3\2\2\2\'\u00a5\3\2\2\2)\u00a7"+
		"\3\2\2\2+\u00aa\3\2\2\2-\u00ad\3\2\2\2/\u00af\3\2\2\2\61\u00b1\3\2\2\2"+
		"\63\u00b4\3\2\2\2\65\u00b7\3\2\2\2\67\u00ba\3\2\2\29\u00bd\3\2\2\2;\u00bf"+
		"\3\2\2\2=\u00c1\3\2\2\2?\u00c3\3\2\2\2A\u00c6\3\2\2\2C\u00c9\3\2\2\2E"+
		"\u00ce\3\2\2\2G\u00d2\3\2\2\2I\u00d9\3\2\2\2K\u00de\3\2\2\2M\u00e3\3\2"+
		"\2\2O\u00e8\3\2\2\2Q\u00ee\3\2\2\2S\u00f1\3\2\2\2U\u00f6\3\2\2\2W\u00fa"+
		"\3\2\2\2Y\u0100\3\2\2\2[\u0106\3\2\2\2]\u010f\3\2\2\2_\u0116\3\2\2\2a"+
		"\u011a\3\2\2\2c\u0120\3\2\2\2e\u012d\3\2\2\2g\u012f\3\2\2\2i\u013b\3\2"+
		"\2\2k\u013d\3\2\2\2m\u0141\3\2\2\2o\u0143\3\2\2\2q\u014b\3\2\2\2s\u014d"+
		"\3\2\2\2u\u014f\3\2\2\2w\u0152\3\2\2\2y\u0159\3\2\2\2{\u015f\3\2\2\2}"+
		"\u016a\3\2\2\2\177\u0080\7*\2\2\u0080\4\3\2\2\2\u0081\u0082\7+\2\2\u0082"+
		"\6\3\2\2\2\u0083\u0084\7}\2\2\u0084\b\3\2\2\2\u0085\u0086\7\177\2\2\u0086"+
		"\n\3\2\2\2\u0087\u0088\7=\2\2\u0088\f\3\2\2\2\u0089\u008a\7.\2\2\u008a"+
		"\16\3\2\2\2\u008b\u008c\7?\2\2\u008c\20\3\2\2\2\u008d\u008e\7]\2\2\u008e"+
		"\22\3\2\2\2\u008f\u0090\7_\2\2\u0090\24\3\2\2\2\u0091\u0092\7-\2\2\u0092"+
		"\u0093\7-\2\2\u0093\26\3\2\2\2\u0094\u0095\7/\2\2\u0095\u0096\7/\2\2\u0096"+
		"\30\3\2\2\2\u0097\u0098\7\60\2\2\u0098\32\3\2\2\2\u0099\u009a\7-\2\2\u009a"+
		"\34\3\2\2\2\u009b\u009c\7/\2\2\u009c\36\3\2\2\2\u009d\u009e\7#\2\2\u009e"+
		" \3\2\2\2\u009f\u00a0\7\u0080\2\2\u00a0\"\3\2\2\2\u00a1\u00a2\7,\2\2\u00a2"+
		"$\3\2\2\2\u00a3\u00a4\7\61\2\2\u00a4&\3\2\2\2\u00a5\u00a6\7\'\2\2\u00a6"+
		"(\3\2\2\2\u00a7\u00a8\7>\2\2\u00a8\u00a9\7>\2\2\u00a9*\3\2\2\2\u00aa\u00ab"+
		"\7@\2\2\u00ab\u00ac\7@\2\2\u00ac,\3\2\2\2\u00ad\u00ae\7>\2\2\u00ae.\3"+
		"\2\2\2\u00af\u00b0\7@\2\2\u00b0\60\3\2\2\2\u00b1\u00b2\7>\2\2\u00b2\u00b3"+
		"\7?\2\2\u00b3\62\3\2\2\2\u00b4\u00b5\7@\2\2\u00b5\u00b6\7?\2\2\u00b6\64"+
		"\3\2\2\2\u00b7\u00b8\7?\2\2\u00b8\u00b9\7?\2\2\u00b9\66\3\2\2\2\u00ba"+
		"\u00bb\7#\2\2\u00bb\u00bc\7?\2\2\u00bc8\3\2\2\2\u00bd\u00be\7(\2\2\u00be"+
		":\3\2\2\2\u00bf\u00c0\7`\2\2\u00c0<\3\2\2\2\u00c1\u00c2\7~\2\2\u00c2>"+
		"\3\2\2\2\u00c3\u00c4\7(\2\2\u00c4\u00c5\7(\2\2\u00c5@\3\2\2\2\u00c6\u00c7"+
		"\7~\2\2\u00c7\u00c8\7~\2\2\u00c8B\3\2\2\2\u00c9\u00ca\7d\2\2\u00ca\u00cb"+
		"\7q\2\2\u00cb\u00cc\7q\2\2\u00cc\u00cd\7n\2\2\u00cdD\3\2\2\2\u00ce\u00cf"+
		"\7k\2\2\u00cf\u00d0\7p\2\2\u00d0\u00d1\7v\2\2\u00d1F\3\2\2\2\u00d2\u00d3"+
		"\7u\2\2\u00d3\u00d4\7v\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7k\2\2\u00d6"+
		"\u00d7\7p\2\2\u00d7\u00d8\7i\2\2\u00d8H\3\2\2\2\u00d9\u00da\7p\2\2\u00da"+
		"\u00db\7w\2\2\u00db\u00dc\7n\2\2\u00dc\u00dd\7n\2\2\u00ddJ\3\2\2\2\u00de"+
		"\u00df\7x\2\2\u00df\u00e0\7q\2\2\u00e0\u00e1\7k\2\2\u00e1\u00e2\7f\2\2"+
		"\u00e2L\3\2\2\2\u00e3\u00e4\7v\2\2\u00e4\u00e5\7t\2\2\u00e5\u00e6\7w\2"+
		"\2\u00e6\u00e7\7g\2\2\u00e7N\3\2\2\2\u00e8\u00e9\7h\2\2\u00e9\u00ea\7"+
		"c\2\2\u00ea\u00eb\7n\2\2\u00eb\u00ec\7u\2\2\u00ec\u00ed\7g\2\2\u00edP"+
		"\3\2\2\2\u00ee\u00ef\7k\2\2\u00ef\u00f0\7h\2\2\u00f0R\3\2\2\2\u00f1\u00f2"+
		"\7g\2\2\u00f2\u00f3\7n\2\2\u00f3\u00f4\7u\2\2\u00f4\u00f5\7g\2\2\u00f5"+
		"T\3\2\2\2\u00f6\u00f7\7h\2\2\u00f7\u00f8\7q\2\2\u00f8\u00f9\7t\2\2\u00f9"+
		"V\3\2\2\2\u00fa\u00fb\7y\2\2\u00fb\u00fc\7j\2\2\u00fc\u00fd\7k\2\2\u00fd"+
		"\u00fe\7n\2\2\u00fe\u00ff\7g\2\2\u00ffX\3\2\2\2\u0100\u0101\7d\2\2\u0101"+
		"\u0102\7t\2\2\u0102\u0103\7g\2\2\u0103\u0104\7c\2\2\u0104\u0105\7m\2\2"+
		"\u0105Z\3\2\2\2\u0106\u0107\7e\2\2\u0107\u0108\7q\2\2\u0108\u0109\7p\2"+
		"\2\u0109\u010a\7v\2\2\u010a\u010b\7k\2\2\u010b\u010c\7p\2\2\u010c\u010d"+
		"\7w\2\2\u010d\u010e\7g\2\2\u010e\\\3\2\2\2\u010f\u0110\7t\2\2\u0110\u0111"+
		"\7g\2\2\u0111\u0112\7v\2\2\u0112\u0113\7w\2\2\u0113\u0114\7t\2\2\u0114"+
		"\u0115\7p\2\2\u0115^\3\2\2\2\u0116\u0117\7p\2\2\u0117\u0118\7g\2\2\u0118"+
		"\u0119\7y\2\2\u0119`\3\2\2\2\u011a\u011b\7e\2\2\u011b\u011c\7n\2\2\u011c"+
		"\u011d\7c\2\2\u011d\u011e\7u\2\2\u011e\u011f\7u\2\2\u011fb\3\2\2\2\u0120"+
		"\u0121\7v\2\2\u0121\u0122\7j\2\2\u0122\u0123\7k\2\2\u0123\u0124\7u\2\2"+
		"\u0124d\3\2\2\2\u0125\u0129\t\2\2\2\u0126\u0128\t\3\2\2\u0127\u0126\3"+
		"\2\2\2\u0128\u012b\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a"+
		"\u012e\3\2\2\2\u012b\u0129\3\2\2\2\u012c\u012e\7\62\2\2\u012d\u0125\3"+
		"\2\2\2\u012d\u012c\3\2\2\2\u012ef\3\2\2\2\u012f\u0133\7$\2\2\u0130\u0132"+
		"\5i\65\2\u0131\u0130\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131\3\2\2\2\u0133"+
		"\u0134\3\2\2\2\u0134\u0136\3\2\2\2\u0135\u0133\3\2\2\2\u0136\u0137\7$"+
		"\2\2\u0137h\3\2\2\2\u0138\u013c\n\4\2\2\u0139\u013a\7^\2\2\u013a\u013c"+
		"\t\5\2\2\u013b\u0138\3\2\2\2\u013b\u0139\3\2\2\2\u013cj\3\2\2\2\u013d"+
		"\u013e\5I%\2\u013el\3\2\2\2\u013f\u0142\5M\'\2\u0140\u0142\5O(\2\u0141"+
		"\u013f\3\2\2\2\u0141\u0140\3\2\2\2\u0142n\3\2\2\2\u0143\u0148\5q9\2\u0144"+
		"\u0147\5s:\2\u0145\u0147\5u;\2\u0146\u0144\3\2\2\2\u0146\u0145\3\2\2\2"+
		"\u0147\u014a\3\2\2\2\u0148\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149p\3"+
		"\2\2\2\u014a\u0148\3\2\2\2\u014b\u014c\t\6\2\2\u014cr\3\2\2\2\u014d\u014e"+
		"\t\7\2\2\u014et\3\2\2\2\u014f\u0150\t\3\2\2\u0150v\3\2\2\2\u0151\u0153"+
		"\t\b\2\2\u0152\u0151\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0152\3\2\2\2\u0154"+
		"\u0155\3\2\2\2\u0155\u0156\3\2\2\2\u0156\u0157\b<\2\2\u0157x\3\2\2\2\u0158"+
		"\u015a\7\17\2\2\u0159\u0158\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b\3"+
		"\2\2\2\u015b\u015c\7\f\2\2\u015c\u015d\3\2\2\2\u015d\u015e\b=\2\2\u015e"+
		"z\3\2\2\2\u015f\u0160\7\61\2\2\u0160\u0161\7\61\2\2\u0161\u0165\3\2\2"+
		"\2\u0162\u0164\n\t\2\2\u0163\u0162\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163"+
		"\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0168\3\2\2\2\u0167\u0165\3\2\2\2\u0168"+
		"\u0169\b>\2\2\u0169|\3\2\2\2\u016a\u016b\7\61\2\2\u016b\u016c\7,\2\2\u016c"+
		"\u0170\3\2\2\2\u016d\u016f\13\2\2\2\u016e\u016d\3\2\2\2\u016f\u0172\3"+
		"\2\2\2\u0170\u0171\3\2\2\2\u0170\u016e\3\2\2\2\u0171\u0173\3\2\2\2\u0172"+
		"\u0170\3\2\2\2\u0173\u0174\7,\2\2\u0174\u0175\7\61\2\2\u0175\u0176\3\2"+
		"\2\2\u0176\u0177\b?\2\2\u0177~\3\2\2\2\16\2\u0129\u012d\u0133\u013b\u0141"+
		"\u0146\u0148\u0154\u0159\u0165\u0170\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}