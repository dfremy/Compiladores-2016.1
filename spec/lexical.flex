package compiler.generated;

import java_cup.runtime.*;

/**
 * Lexical Specification
 *
 * @author Isabelly Cavalcante, Remy De Fru, Anderson Gustafson (2016.1)
 */
 
%%

%class Scanner
%public
%unicode
%line
%column
%cup
%cupdebug

%{
	StringBuilder string = new StringBuilder();
	public static String curLine;

	private Symbol symbol(int type) {
		return new JavaSymbol(type, yyline+1, yycolumn+1);
	}
	
	private Symbol symbol(int type, Object value) {
		return new JavaSymbol(type, yyline+1, yycolumn+1, value);
	}
  
	private long parseLong(int start, int end, int radix) {
		long result = 0;
		long digit;

		for (int i = start; i < end; i++) {
			digit  = Character.digit(yycharat(i),radix);
			result *= radix;
			result += digit;
		}
		return result;
	}

	/* Mensagem de erro lexico em uma dada linha */
	private void reportError(int line, String msg) {
		throw new RuntimeException("Erro lexico na linha" + line + ": " + msg);
	}
%}


/* Id */
Identifier = {Letter_}({Letter}|{Alphanumerics_})*

Letter = [a-zA-Z]
Letter_ = {Letter}|_
Alphanumerics_ = [ a-zA-Z0-9_]

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

/* espa�o */
WhiteSpace = {LineTerminator} | [ \t\f]

/* Literais */
DecimalIntegerLiteral = 0 | [1-9][0-9]*

HexIntegerLiteral = 0 [xX] {HexDigit}+
HexDigit = [0-9a-fA-F]

OctalIntegerLiteral = 0 {OctalDigit}+
OctalDigit = [0-7]


FloatLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1 = [0-9]+ \. [0-9]* 
FLit2 = \. [0-9]+ 
FLit3 = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

Marker = \" | \'
Other_Symbols = \*|\+|\[|\]|\!|\£|\$|\%|\&|\=|\?|\^|\-|\°|\#|\@|\:|\(|\)
Separators = \r|\n|\r\n\t\f

StringLiteral = {Marker} {StringContent} {Marker}
StringContent = {Alphanumerics_}*StringContent | {Other_Symbols}*StringContent | {Separators}*StringContent

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

%%
<YYINITIAL> {

	/*Palavras reservadas */

	"abstract"					{ return symbol(ABSTRACT); }
	"assert"					{ return symbol(ASSERT); }
	"boolean"					{ return symbol(BOOLEAN); }
	"break"						{ return symbol(BREAK); }
	"byte"						{ return symbol(BYTE); }
	"case"						{ return symbol(CASE); }
	"catch"						{ return symbol(CATCH); }
	"char"						{ return symbol(CHAR); }
	"class"						{ return symbol(CLASS); }
	"const"						{ return symbol(CONST); }
	"continue"					{ return symbol(CONTINUE); }
	"default"					{ return symbol(DEFAULT); }
	"do"						{ return symbol(DO); }
	"double"					{ return symbol(DOUBLE); }
	"else"						{ return symbol(ELSE); }
	"enum"						{ return symbol(ENUM); }
	"extends"					{ return symbol(EXTENDS); }
	"final"						{ return symbol(FINAL); }
	"finally"					{ return symbol(FINALLY); }
	"float"						{ return symbol(FLOAT); }
	"for"						{ return symbol(FOR); }
	"if"						{ return symbol(IF); }
	"goto"						{ return symbol(GOTO); }
	"implements"				{ return symbol(IMPLEMENTS); }
	"import"					{ return symbol(IMPORT); }
	"instanceof"				{ return symbol(INSTANCEOF); }
	"int"						{ return symbol(INT); }
	"interface"					{ return symbol(INTERFACE); }
	"long"						{ return symbol(LONG); }
	"native"					{ return symbol(NATIVE); }
	"new"						{ return symbol(NEW); }
	"package"					{ return symbol(PACKAGE); }
	"private"					{ return symbol(PRIVATE); }
	"protected"					{ return symbol(PROTECTED); }
	"public"					{ return symbol(PUBLIC); }
	"return"					{ return symbol(RETURN); }
	"short"						{ return symbol(SHORT); }
	"static"					{ return symbol(STATIC); }
	"super"						{ return symbol(SUPER); }
	"switch"					{ return symbol(SWITCH); }
	"synchronized"				{ return symbol(SYNCHRONIZED); }
	"this"						{ return symbol(THIS); }
	"threadsafe"				{ return symbol(THREADSAFE); }
	"throw"						{ return symbol(THROW); }
	"throws"					{ return symbol(THROWS); }
	"transient"					{ return symbol(TRANSIENT); }
	"try"						{ return symbol(TRY); }
	"void"						{ return symbol(VOID); }
	"volatile"					{ return symbol(VOLATILE); }
	"while"						{ return symbol(WHILE); }

	"true"						{ return symbol(BOOLEAN_LITERAL, true); }
	"false"						{ return symbol(BOOLEAN_LITERAL, false); }
	"null"						{ return symbol(NULL); }

	{Identifier}				{ return symbol(IDENTIFIER, yytext()); }

	{DecimalIntegerLiteral}		{ return symbol(INTEGER_LITERAL, new Integer(yytext())); }
	{HexIntegerLiteral}			{ return symbol(INTEGER_LITERAL, new Integer((int) parseLong(2, yylength(), 16))); }
	{OctalIntegerLiteral}		{ return symbol(INTEGER_LITERAL, new Integer((int) parseLong(0, yylength(), 8))); }
	{FloatLiteral}				{ return symbol(FLOAT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
	{DoubleLiteral}				{ return symbol(FLOAT_LITERAL, new Double(yytext())); }

	{StringLiteral}				{ return symbol(STRING_LITERAL, new String(yytext())); }


	\'							{ return symbol(CHARLITERAL); }
	{Comment}					{ /* ignore */ }
	{WhiteSpace}				{ /* ignore */}

	"("							{ return symbol(LPAREN); }
	")"							{ return symbol(RPAREN); }
	"{"							{ return symbol(LBRACE); }
	"}"							{ return symbol(RBRACE); }
	"["							{ return symbol(LBRACK); }
	"]"							{ return symbol(RBRACK); }
	";"							{ return symbol(SEMICOLON); }
	":"							{ return symbol(COLON); }
	","							{ return symbol(COMMA); }
	"."							{ return symbol(DOT); }
	"?"							{ return symbol(QUESTION); }


/* Arithmetical op */
	"+"							{ return symbol(PLUS); }
	"-"							{ return symbol(MINUS); }
	"*"							{ return symbol(MULT); }
	"/"							{ return symbol(DIV); }
	"%"							{ return symbol(MOD); }

/* Others */

	"="							{ return symbol(EQ); }
	"<"							{ return symbol(LT); }
	">"							{ return symbol(GT); }
	"!"							{ return symbol(NOT); }
 	"~"							{ return symbol(COMP); }
	"=="						{ return symbol(EQEQ); }
	">="						{ return symbol(GTEQ); }
	"<="						{ return symbol(LTEQ); }
	"||"						{ return symbol(OROR); }
	"&&"						{ return symbol(ANDAND); }
	"++"						{ return symbol(PLUSPLUS); }
	"--"						{ return symbol(MINUSMINUS); }
	"&"							{ return symbol(AND); }
	"|"							{ return symbol(OR); }
	"^"							{ return symbol(XOR); }
	"<<"						{ return symbol(LSHIFT); }
	">>"						{ return symbol(RSHIFT); }
	">>>"						{ return symbol(URSHIFT); }
	"-="						{ return symbol(PLUSEQ); }
	"+="						{ return symbol(MINUSEQ); }
	"*="						{ return symbol(MULTEQ); }
	"/="						{ return symbol(DIVEQ); }
	"%="						{ return symbol(MODEQ); }
	"&="						{ return symbol(ANDEQ); }
	"^="						{ return symbol(XOREQ); }
	"|="						{ return symbol(OREQ); }
	">>="						{ return symbol(RSHIFTEQ); }
	"<<="						{ return symbol(LSHIFTEQ); }
	"!="						{ return symbol(NOTEQ); }
	">>>="						{ return symbol(URSHIFTEQ); }

 }

/* Se não entrou em nenhum outro */
[^]								{ reportError(yyline+1, "Illegal character \"" + yytext() + "\""); }