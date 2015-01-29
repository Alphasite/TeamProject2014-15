package uk.ac.gla.teamL.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static uk.ac.gla.teamL.psi.EBNFTypes.*;

%%

%{
  public _EBNFLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _EBNFLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

COMMENT_SINGLELINE="//"[^\n]*
COMMENT_BLOCK="/"\*[^*/\*]*\*"/"
STRING_TRIPLEQUOTES='''([^(''')]|(\'))*'''
STRING_DOUBLEQUOTES=\"([^\"\\\"]|\\.)*\"
STRING_SINGLEQUOTES='([^(')]|(\\\'))*'
ID=[a-zA-Z][_a-zA-Z0-9]*
NUMBERS=[0-9]+

%%
<YYINITIAL> {
  {WHITE_SPACE}              { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "let"                      { return LET; }
  "="                        { return EQ; }
  "|"                        { return OR_OPERATOR; }
  "("                        { return LB; }
  ")"                        { return RB; }
  "{"                        { return LCB; }
  "}"                        { return RCB; }
  "["                        { return LSB; }
  "]"                        { return RSB; }
  "*"                        { return ZERO_OR_MORE; }
  "?"                        { return ZERO_OR_ONE; }
  "+"                        { return ONE_OR_MORE; }
  ".."                       { return RANGE_OPERATOR; }
  "!"                        { return NEGATION_OPERATOR; }
  "."                        { return ANY_OPERATOR; }
  ","                        { return LIST_SEPERATOR; }
  ";"                        { return TERMINAL; }
  "@"                        { return ANNOTATION_OPERATOR; }

  {COMMENT_SINGLELINE}       { return COMMENT_SINGLELINE; }
  {COMMENT_BLOCK}            { return COMMENT_BLOCK; }
  {STRING_TRIPLEQUOTES}      { return STRING_TRIPLEQUOTES; }
  {STRING_DOUBLEQUOTES}      { return STRING_DOUBLEQUOTES; }
  {STRING_SINGLEQUOTES}      { return STRING_SINGLEQUOTES; }
  {ID}                       { return ID; }
  {NUMBERS}                  { return NUMBERS; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
