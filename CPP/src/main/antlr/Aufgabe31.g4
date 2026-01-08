grammar Aufgabe31;

// Parser
// programm besteht aus 0 oder mehr Statements, bis zum Ende der Datei (EOF)
program :  statement* EOF ;

// while und if
loop   :  'while' comparison 'do' statement+;
conditional : 'if' comparison 'do' statement+ ('else' 'do' statement+)? 'end' ;
// Variablen (ohne typen wie in dem Beispiel)
assign  :  ID ASSIGN (idOrNum | STRING);
// comparison hat am wenigsten Vorrang, Multiplikation z.B. am meisten
expr : comparison | STRING ; // String hier einzeln, damit man keine Strings multiplizieren kann
comparison : (addition  (EQUAL | NEQUAL | LESSTHAN | GREATERTHAN) addition) | (STRING (EQUAL | NEQUAL) STRING) ;
addition : multiplication ( (PLUS | MINUS) multiplication )* ;
multiplication : idOrNum ( (MUL | DIV) idOrNum )* ; // Multiplikation mit IDs oder Zahlen, aber ohne Strings
// 1 == 1 == 1 >= 1
idOrNum : ID
    | NUMBER
    ;

statement: assign
    |  loop
    |  conditional
    ;

// Lexer
ID      :  [a-z][a-zA-Z0-9]* ;
ASSIGN  :   ':=' ;
NUMBER  :  [0-9]+ ;
// String: Anfang mit " und Ende mit ", alles erlaubt ausser newlines
STRING  :  '"' (~[\n\r"])* '"' ;
GREATERTHAN: '>';
LESSTHAN: '<';
PLUS    : '+';
MINUS   : '-';
DIV     : '/';
MUL     : '*';
EQUAL   : '==';
NEQUAL  : '!=';
// Kommentare: Anfang Schraegstriche, danach alles was kein newline ist
COMMENT : '#' ~[\r\n]* -> skip;
WS      : [ \t]+ -> skip ;
NEWLINE : [\r\n]+ -> skip;

/* Beispiel
a := 0
if 10 < 1 do
    a := 42
else do
    a := 7
end
*/
