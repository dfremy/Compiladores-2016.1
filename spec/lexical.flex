package compiler.generated;

import java_cup.runtime.*;
import sym.
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

	"abstract"					{ return symbol(sym.ABSTRACT); }
	"assert"					{ return symbol(sym.ASSERT); }
	"boolean"					{ return symbol(sym.BOOLEAN); }
	"break"						{ return symbol(sym.BREAK); }
	"byte"						{ return symbol(sym.BYTE); }
	"case"						{ return symbol(sym.CASE); }
	"catch"						{ return symbol(sym.CATCH); }
	"char"						{ return symbol(sym.CHAR); }
	"class"						{ return symbol(sym.CLASS); }
	"const"						{ return symbol(sym.CONST); }
	"continue"					{ return symbol(sym.CONTINUE); }
	"default"					{ return symbol(sym.DEFAULT); }
	"do"						{ return symbol(sym.DO); }
	"double"					{ return symbol(sym.DOUBLE); }
	"else"						{ return symbol(sym.sym.ELSE); }
	"enum"						{ return symbol(sym.ENUM); }
	"extends"					{ return symbol(sym.EXTENDS); }
	"final"						{ return symbol(sym.FINAL); }
	"finally"					{ return symbol(sym.FINALLY); }
	"float"						{ return symbol(sym.FLOAT); }
	"for"						{ return symbol(sym.FOR); }
	"if"						{ return symbol(sym.IF); }
	"goto"						{ return symbol(sym.GOTO); }
	"implements"				{ return symbol(sym.IMPLEMENTS); }
	"import"					{ return symbol(sym.IMPORT); }
	"instanceof"				{ return symbol(sym.INSTANCEOF); }
	"int"						{ return symbol(sym.INT); }
	"interface"					{ return symbol(sym.INTERFACE); }
	"long"						{ return symbol(sym.LONG); }
	"native"					{ return symbol(sym.NATIVE); }
	"new"						{ return symbol(sym.NEW); }
	"package"					{ return symbol(sym.PACKAGE); }
	"private"					{ return symbol(sym.PRIVATE); }
	"protected"					{ return symbol(sym.PROTECTED); }
	"public"					{ return symbol(sym.PUBLIC); }
	"return"					{ return symbol(sym.RETURN); }
	"short"						{ return symbol(sym.SHORT); }
	"static"					{ return symbol(sym.STATIC); }
	"super"						{ return symbol(sym.SUPER); }
	"switch"					{ return symbol(sym.SWITCH); }
	"synchronized"				{ return symbol(sym.SYNCHRONIZED); }
	"this"						{ return symbol(sym.THIS); }
	"threadsafe"				{ return symbol(sym.THREADSAFE); }
	"throw"						{ return symbol(sym.THROW); }
	"throws"					{ return symbol(sym.THROWS); }
	"transient"					{ return symbol(sym.TRANSIENT); }
	"try"						{ return symbol(sym.TRY); }
	"void"						{ return symbol(sym.VOID); }
	"volatile"					{ return symbol(sym.VOLATILE); }
	"while"						{ return symbol(sym.WHILE); }

	"true"						{ return symbol(sym.BOOLEAN_LITERAL, true); }
	"false"						{ return symbol(sym.BOOLEAN_LITERAL, false); }
	"null"						{ return symbol(sym.NULL); }

	{Identifier}				{ return symbol(sym.IDENTIFIER, yytext()); }

	{DecimalIntegerLiteral}		{ return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }
	{HexIntegerLiteral}			{ return symbol(sym.INTEGER_LITERAL, new Integer((int) parseLong(2, yylength(), 16))); }
	{OctalIntegerLiteral}		{ return symbol(sym.INTEGER_LITERAL, new Integer((int) parseLong(0, yylength(), 8))); }
	{FloatLiteral}				{ return symbol(sym.FLOAT_LITERAL, new Float(yytext().substring(0,yylength()-1))); }
	{DoubleLiteral}				{ return symbol(sym.FLOAT_LITERAL, new Double(yytext())); }

	{StringLiteral}				{ return symbol(sym.STRING_LITERAL, new String(yytext())); }


	\'							{ return symbol(sym.CHARLITERAL); }
	{Comment}					{ /* ignore */ }
	{WhiteSpace}				{ /* ignore */}

	"("							{ return symbol(sym.LPAREN); }
	")"							{ return symbol(sym.RPAREN); }
	"{"							{ return symbol(sym.LBRACE); }
	"}"							{ return symbol(sym.RBRACE); }
	"["							{ return symbol(sym.LBRACK); }
	"]"							{ return symbol(sym.RBRACK); }
	";"							{ return symbol(sym.SEMICOLON); }
	":"							{ return symbol(sym.COLON); }
	","							{ return symbol(sym.COMMA); }
	"."							{ return symbol(sym.DOT); }
	"?"							{ return symbol(sym.QUESTION); }


/* Arithmetical op */
	"+"							{ return symbol(sym.PLUS); }
	"-"							{ return symbol(sym.MINUS); }
	"*"							{ return symbol(sym.MULT); }
	"/"							{ return symbol(sym.DIV); }
	"%"							{ return symbol(sym.MOD); }

/* Others */

	"="							{ return symbol(sym.EQ); }
	"<"							{ return symbol(sym.LT); }
	">"							{ return symbol(sym.GT); }
	"!"							{ return symbol(sym.NOT); }
 	"~"							{ return symbol(sym.COMP); }
	"=="						{ return symbol(sym.EQEQ); }
	">="						{ return symbol(sym.GTEQ); }
	"<="						{ return symbol(sym.LTEQ); }
	"||"						{ return symbol(sym.OROR); }
	"&&"						{ return symbol(sym.ANDAND); }
	"++"						{ return symbol(sym.PLUSPLUS); }
	"--"						{ return symbol(sym.MINUSMINUS); }
	"&"							{ return symbol(sym.AND); }
	"|"							{ return symbol(sym.OR); }
	"^"							{ return symbol(sym.XOR); }
	"<<"						{ return symbol(sym.LSHIFT); }
	">>"						{ return symbol(sym.RSHIFT); }
	">>>"						{ return symbol(sym.URSHIFT); }
	"-="						{ return symbol(sym.PLUSEQ); }
	"+="						{ return symbol(sym.MINUSEQ); }
	"*="						{ return symbol(sym.MULTEQ); }
	"/="						{ return symbol(sym.DIVEQ); }
	"%="						{ return symbol(sym.MODEQ); }
	"&="						{ return symbol(sym.ANDEQ); }
	"^="						{ return symbol(sym.XOREQ); }
	"|="						{ return symbol(sym.OREQ); }
	">>="						{ return symbol(sym.RSHIFTEQ); }
	"<<="						{ return symbol(sym.LSHIFTEQ); }
	"!="						{ return symbol(sym.NOTEQ); }
	">>>="						{ return symbol(sym.URSHIFTEQ); }

 }

/* Se não entrou em nenhum outro */
[^]								{ reportError(yyline+1, "Illegal character \"" + yytext() + "\""); }