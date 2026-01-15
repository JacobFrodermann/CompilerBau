grammar cpp;
start: stmt*;

stmt: (definition | declaration | ws)+;

definition: functionDef;
declaration: functionDecl;

// functions
functionBody: expr*;
expr: (functionCall | ';' | ws | returnSTMT);

functionDef:  OBJ_NAME space OBJ_NAME args ws* '{' ws* functionBody ws* '}';
functionDecl: OBJ_NAME space OBJ_NAME args space ';';
functionCall: OBJ_NAME args;

args: '('(arg (optSpace ',' optSpace arg)* )? ')';
arg: (OBJ_NAME space OBJ_NAME);

// variables
varDecl: OBJ_NAME space OBJ_NAME space ';';
varDef: OBJ_NAME space OBJ_NAME space '=' space value;

value: (functionCall | literal | comparison);
literal: (stringLiteral | intLiteral | boolLiteral);

intLiteral: NUM;

// bool stuff
boolLiteral: 'true' | 'false';

// operations
comparison: (functionCall | literal) space ('==' | '=<' | '=>' | '>' | '<') space (functionCall | literal);
bitwiseOP: '|' | '&';

//return
returnSTMT: 'return' (space expr)? optSpace ';';

// literals
stringLiteral: '"' ('\\'?.) '"';

ws:(' ' | '\n');
space: ' '+;
optSpace: ' '*;

OBJ_NAME:[a-zA-Z][a-zA-Z0-9]*;
CHAR: [a-zA-Z0-9];
NUM: [0-9];
