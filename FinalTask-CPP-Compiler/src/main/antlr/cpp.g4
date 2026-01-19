grammar cpp;

start: stmt* EOF;

stmt: (definition | declaration)+;

definition: functionDef | classDefinition | varDef;
declaration: functionDecl | varDecl;

// functions

functionBody: expr*;

expr: (assignment | functionCall | ';' | returnSTMT);

assignment: OBJ_NAME '=' value ';';

functionDef: OBJ_NAME OBJ_NAME args '{' functionBody '}';
functionDecl: OBJ_NAME OBJ_NAME args ';';
functionCall: OBJ_NAME args;

// classes

classDefinition: 'class' OBJ_NAME '{' 'public' ':' classBody '}' ';';

classBody: (fieldDecl | constructorDef | methodDef)*;

fieldDecl: OBJ_NAME OBJ_NAME ';';

constructorDef: OBJ_NAME '(' ')' '{' functionBody '}';

methodDef: OBJ_NAME OBJ_NAME '(' ')' '{' functionBody '}';

args: '(' (arg (',' arg)*)? ')';
arg: OBJ_NAME OBJ_NAME;

// flowControll

flowControll: if | while;
if: 'if'WS'(' value ')'WS'{'WS expr* WS '}'(WS 'else' WS '{'expr* '}')?;
while:'while'WS'(' value ')'WS'{'WS expr* WS '}';

// Variabels

varDecl: OBJ_NAME OBJ_NAME ';';
varDef: OBJ_NAME OBJ_NAME '=' value ';';

value: (memberAccess | functionCall | literal | comparison | OBJ_NAME);
memberAccess: OBJ_NAME '.' OBJ_NAME;

// literals

literal: (stringLiteral | intLiteral | boolLiteral);

intLiteral: '-'? NUM;

boolLiteral: 'true' | 'false';

comparison: (functionCall | literal | OBJ_NAME) ('==' | '<=' | '>=' | '!=' | '>' | '<') (functionCall | literal | OBJ_NAME);

returnSTMT: 'return' expr? ';';

stringLiteral: '"' .*? '"';


NUM: [0-9]+;
OBJ_NAME: [a-zA-Z][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;
