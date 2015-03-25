// Auto Generated Code. 

grammar ebnf;

options {
	output=AST;
}

// Literals 
tokens { 
    PREDICATE = '!';
    ANY = '.';
}

// Parser Rules 
program: assignment* ^( 'program' assignment );

assignment: annotation* 'let' identifier '=' rules ';' ^( 'assignment' annotation identifier rules );

rules: ruleElement 
     ( '|'? ruleElement
     )* ^( 'rules' ruleElement* );

ruleElement: PREDICATE? 
           ( string 
           | identifier 
           | range 
           | nestedRules 
           | ANY 
           ) quantifier? ^( 'ruleElement' quantifier range ANY nestedRules string PREDICATE identifier );

nestedRules: '(' rules ')' ^( 'nestedRules' rules );

quantifier: '*' 
          | '?' 
          | '+' 
          | arbitraryQuantifier ^( 'quantifier' arbitraryQuantifier );

annotation: '@' identifier ^( 'annotation' identifier );

arbitraryQuantifier: '{' NUMBER 
                   ( ',' NUMBER 
                   )? '}' ^( 'arbitraryQuantifier' NUMBER* );

range: '[' string '..' ']' ^( 'range' string );

identifier: CHARACTER+ ^( 'identifier' CHARACTER );

string: 
      ( '"' 
      | '\'' 
      ) 
      ( CHARACTER 
      | NUMBER 
      | WHITESPACE 
      )* 
      ( '"' 
      | '\'' 
      ) ^( 'string' WHITESPACE NUMBER CHARACTER );


// Lexer Rules 
CHARACTER: 'A'..'z';
NUMBER: '0'..'9';
LINEBREAK: '\n' | '\r' {$channel=HIDDEN;};
WHITESPACE: ' ' | '\t' {$channel=HIDDEN;};
LINECOMMENT: '//' 
           ( CHARACTER 
           | NUMBER 
           | WHITESPACE 
           )* {$channel=HIDDEN;};
BLOCKCOMMENT: '/*' 
            ( CHARACTER 
            | NUMBER 
            | WHITESPACE 
            | LINEBREAK 
            )* '*/' {$channel=HIDDEN;};
