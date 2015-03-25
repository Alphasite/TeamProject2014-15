// Auto Generated Code. 

grammar ebnf;

// Parser Rules 
comment: "//" 
       ( character 
       | number 
       | whitespace 
       )*;	 { $channel=HIDDEN; }

string: "\"" 
      ( character 
      | number 
      | whitespace 
      )* "\"";

program: assignment*;

assignment: "let" identifier "=" rules ";";

rules: ruleElement ( "|"? ruleElement )*;

ruleElement: predicate? 
           ( string 
           | identifier 
           | range 
           | nestedRules 
           | any 
           ) quantifier?;

nestedRules: "(" rules ")";

quantifier: "*" | "?" | "+" | arbitraryQuantifier;

arbitraryQuantifier: "{" number ( "," number )? "}";

range: "[" string ".." string "]";

identifier: character+;


// Lexer Rules 
CHARACTER: 'A'..'z';
NUMBER: '0'..'9';
WHITESPACE: "n" | "t";
PREDICATE: "!";
ANY: ".";
