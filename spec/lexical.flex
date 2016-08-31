package compiler.generated;

import java_cup.runtime.*;

/**
 * Lexical Specification
 *
 */
 
%%

%class Scanner
%public
%unicode
%line
%column
%cup

%{

	public static String curLine;

	public Symbol symbol(int type) {
		curLine = "line :" + yyline;
		return new Symbol(type, yyline, yycolumn);
	}
	
	public Symbol symbol(int type, Object value) {
		curLine = "line :" + yyline;
		return new Symbol(type, yyline, yycolumn, value);
	}
  
	/* Mensagem de erro lexico em uma dada linha */
	private void reportError(int line, String msg) {
		throw new RuntimeException("Lexical error at line #" + line + ": " + msg);
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
IntegerLiteral = {DecimalIntegerLiteral} | {HexIntegerLiteral} | {OctalIntegerLiteral}
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

StringLiteral = {Marker}  {StringContent}  {Marker}
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
	"else"						{ return symbol(sym.ELSE); }
	"enum"						{ return symbol(sym.ENUM); }
	"extends"					{ return symbol(sym.EXTENDS); }
	"false"						{ return symbol(sym.FALSE); }
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
	"null"						{ return symbol(sym.NULL); }
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
	"true"						{ return symbol(sym.TRUE); }
	"try"						{ return symbol(sym.TRY); }
	"void"						{ return symbol(sym.VOID); }
	"volatile"					{ return symbol(sym.VOLATILE); }
	"while"						{ return symbol(sym.WHILE); }


	{Identifier}				{ return symbol(sym.IDENTIFIER, yytext()); }

	{IntegerLiteral}			{ return symbol(sym.INTEGER_LITERAL, new String(yytext())); }
	{FloatLiteral}				{ return symbol(sym.FLOAT_LITERAL, new String(yytext())); }
	{DoubleLiteral}				{ return symbol(sym.DOUBLE_LITERAL, new String(yytext())); }
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

 /* Logical op */
	"=="						{ return symbol(sym.EQEQ); }
	">="						{ return symbol(sym.GTEQ); }
	"<="						{ return symbol(sym.LTEQ); }
	"<"							{ return symbol(sym.LT); }
	">"							{ return symbol(sym.GT); }
	"||"						{ return symbol(sym.OROR); }
	"&&"						{ return symbol(sym.ANDAND); }
	"&"							{ return symbol(sym.AND); }
	"!"							{ return symbol(sym.NOT); }
	"!="						{ return symbol(sym.NOTEQ); }
	"|"							{ return symbol(sym.OR); }
	"^"							{ return symbol(sym.XOR); }
	">>>"						{ return symbol(sym.URSHIFT); }
	"<<"						{ return symbol(sym.LSHIFT); }
	">>"						{ return symbol(sym.RSHIFT); }
	">>>="						{ return symbol(sym.URSHIFTEQ); }

/* Others */
	"="							{ return symbol(sym.ASSIGNMENT, new String(yytext())); }
	"-="						{ return symbol(sym.MINUSASSIGN, new String(yytext())); }
	"+="						{ return symbol(sym.PLUSASSIGN, new String(yytext())); }
	"*="						{ return symbol(sym.MULTASSIGN); }
	"/="						{ return symbol(sym.DIVASSIGN); }
	"%="						{ return symbol(sym.MODASSIGN); }
	"&="						{ return symbol(sym.ANDASSIGN); }
	"^="						{ return symbol(sym.XORASSIGN); }
	"|="						{ return symbol(sym.ORASSIGN); }
	">>="						{ return symbol(sym.RSHIFTASSIGN, new String(yytext())); }
	"<<="						{ return symbol(sym.LSHIFTASSIGN, new String(yytext())); }
	"++"						{ return symbol(sym.AUTOINCRM); }
	"--"						{ return symbol(sym.AUTODECRM); }

 }

/* Se não entrou em nenhum outro */
[^]								{ reportError(yyline+1, "Illegal character \"" + yytext() + "\""); }