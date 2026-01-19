grammar cpp;

start: stmt* EOF;

stmt: (functionDef | classDefinition | varDef | functionDecl | varDecl)+;

// functions

functionBody: statement*;

statement: (assignment | functionCall | ifStmt | whileStmt | returnSTMT | block | ';');

block: '{' statement* '}';

assignment: (memberAccess | OBJ_NAME) '=' value ';';

functionDef: OBJ_NAME OBJ_NAME args '{' functionBody '}';
functionDecl: OBJ_NAME OBJ_NAME args ';';
functionCall: OBJ_NAME args ';';

args: '(' (arg (',' arg)*)? ')';
arg: OBJ_NAME OBJ_NAME;

// classes

classDefinition: 'class' OBJ_NAME '{' 'public' ':' classBody '}' ';';

classBody: (fieldDecl | constructorDef | methodDef)*;

fieldDecl: OBJ_NAME OBJ_NAME ';';

constructorDef: OBJ_NAME '(' ')' '{' functionBody '}';

methodDef: 'virtual'? OBJ_NAME OBJ_NAME '(' ')' '{' functionBody '}';

// flow control

ifStmt: 'if' '(' value ')' statement ('else' statement)?;

whileStmt: 'while' '(' value ')' statement;

// variables

varDecl: OBJ_NAME OBJ_NAME ';';
varDef: OBJ_NAME OBJ_NAME '=' value ';';

value: (memberAccess | functionCall | literal | comparison | OBJ_NAME);

memberAccess: OBJ_NAME '.' OBJ_NAME;

// literals

literal: (STRING_LITERAL | intLiteral | boolLiteral);

intLiteral: NUM;

boolLiteral: 'true' | 'false';

// operations

comparison: (functionCall | literal | OBJ_NAME) ('==' | '<=' | '>=' | '!=' | '>' | '<') (functionCall | literal | OBJ_NAME);

// return

returnSTMT: 'return' value? ';';

// lexer

STRING_LITERAL: '"' (~["\\\r\n] | '\\' .)* '"';
NUM: '-'? [0-9]+;
OBJ_NAME: [a-zA-Z][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;
