// Auto Generated Code. 

grammar short;

options {
	output=AST;
}

// Literals 
tokens { 
    EQUALS = '=';
}

// Parser Rules 
assignment: ID '=' INT ^( 'assignment' ID INT );

test: '0'..'8' . ^( 'test' );


// Lexer Rules 
ID: 
  ( 'a'..'z' 
  | 'A'..'Z' 
  | '_' 
  ) 
  ( 'a'..'z' 
  | 'A'..'Z' 
  | '0'..'9' 
  | '_' 
  )*;
INT: '0'..'9'+;
WS: ( ' ' | '\t' | '\r' | '\n' ) {$channel=HIDDEN;};
