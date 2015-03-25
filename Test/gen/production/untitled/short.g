// Auto Generated Code. 

grammar short;

// Parser Rules 
assignment: ID '=' INT;


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
WS: 
  ( ' ' 
  | '\t' 
  | '\r' 
  | '\n' 
  ) 	 { $channel=HIDDEN; };
