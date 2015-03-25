// Auto Generated Code. 

grammar test;

// Literals 
tokens { 
    EEEEE = 'asdas \' ';
    DFAS = 'dasda';
}

// Parser Rules 
asd: ( asd );

ccc: ( A null ) | C;

h: 's'..'d' banana;

toast: null;

banana: asd;

ajsd: BACON null;


// Lexer Rules 
A: A | null | C;
C: 'bbasdas';
A: 'bbasdas' banana {$channel=HIDDEN;};
BB: BB | 'aaa' | asd {$channel=HIDDEN;};
DDDD: 'Asdasd \\"';
F: 'asdasdasd'*;
G: ~( 'x' | 'x' | 's' | 's' | 'a' );
BACON: 'a' 'a' 'a' 'a' 'a' ('a'('a'('a'('a')?)?)?)?;
