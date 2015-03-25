// Auto Generated Code. 

grammar test;

options {
	output=AST;
}

// Literals 
tokens { 
    EEEEE = 'asdas \\' ';
    DFAS = 'dasda';
}

// Parser Rules 
a: a | null | C ^( 'a' null C a );

asd: ( asd ) ^( 'asd' asd );

ccc: ( a null ) | C ^( 'ccc' null C a );

h: 's'..'d' banana ^( 'h' banana );

toast: null ^( 'toast' null );

banana: asd ^( 'banana' asd );

ajsd: BACON null ^( 'ajsd' null BACON );

m: n ^( 'm' n );

n: o ^( 'n' o );

o: m ^( 'o' m );


// Lexer Rules 
C: 'bbasdas';
PA: 'bbasdas' banana {$channel=HIDDEN;};
BB: BB | 'aaa' | asd {$channel=HIDDEN;};
DDDD: 'Asdasd \"';
F: 'asdasdasd'*;
G: ~( 'x' | 'x' | 's' | 's' | 'a' );
BACON: 'a' 'a' 'a' 'a' 'a' ('a'('a'('a'('a')?)?)?)?;
