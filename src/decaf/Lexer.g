header {package decaf;}

options 
{
  mangleLiteralPrefix = "TK_";
  language="Java";
}

class DecafScanner extends Lexer;
options 
{
   charVocabulary = '\32'..'\126';
   testLiterals=false;    // don't automatically test for literals
   k=2;
}

tokens 
{
  "if"      ;
  "else"    ;
  "for"     ;
  "break"   ;
  "continue";
  "return"  ;
  "boolean" ;
  "int"     ;
  "void"    ;
  "class"   ;
  "callout" ;
  "true"    ;
  "false"   ;
}


WS_ : (' ' | '\n' {newline();} | '\t' ) {_ttype = Token.SKIP; };

SL_COMMENT : '/' '/' (~'\n')* '\n' {_ttype = Token.SKIP; newline (); };

CHARLITERAL : '\'' ('('..'[' | ']'..'~' | '#'..'&' | ' ' | '!' | '\\' ('n' | 't' | '\\' | '\'' | '"')) '\'';
STRINGLITERAL : '"' ('('..'[' | ']'..'~' | '#'..'&' | ' ' | '!' | '\\' ('n' | 't' | '\\' | '\'' | '"'))* '"';


IDENTIFIER options {testLiterals=true;} : IDENT_LETTER (IDENT_SYMBOL)*;
protected IDENT_LETTER : 'a'..'z' | 'A'..'Z' | '_';
protected IDENT_SYMBOL : IDENT_LETTER | '0'..'9';



INTLITERAL : ('0'..'9')+ | ('0' 'x' ('0'..'9' | 'a'..'f' | 'A'..'F')+);

BOOLEANLITERAL : ("true" | "false") (IDENT_SYMBOL {$setType(IDENTIFIER);})*;

// KEYWORD :
//             "if"
//             | "else"
//             | "for"
//             | "break"
//             | "continue"
//             | "return"
//             | "boolean"
//             | "int"
//             | "void"
//             | "class"
//             | "callout";




// IF       : "if";
// ELSE     : "else";
// FOR      : "for";
// BREAK    : "break";
// CONTINUE : "continue";
// RETURN   : "return";
// TYPE     : "boolean" | "int";
// VOID     : "void";
// CLASS    : "class";
// CALLOUT  : "callout";

ARITH_OP : '+' | '-' | '*' | '/' | '%';
ASSIGN_OP : '=' | "+=" | "-=";

REL_OP : '<' | '>' | "<=" | ">=";
EQ_OP : "==" | "!=";
COND_OP : "&&" | "||";



LCURLY : '{';
RCURLY : '}';
LBRACE : '(';
RBRACE : ')';
LSQUARE : '[';
RSQUARE : ']';
COMA : ',';
DOTCOMA : ';';

//protected
//SEPARTORS : LCURLY | RCURLY | LBRACE | RBRACE | LSQUARE | RSQUARE | COMA | DOTCOMA


//fragment CHAR :  '('..'~' | '#'..'&' | ' ' | '!' | '\n' | '\t' | '\\' | '\'' | '\"';
/*

STRING : '"' (ESC|~'"')* '"';

protected
ESC :  '\\' ('n'|'"');
*/