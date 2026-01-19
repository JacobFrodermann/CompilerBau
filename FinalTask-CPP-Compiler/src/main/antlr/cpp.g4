grammar cpp;

start: toplvl* EOF;

toplvl: (functionDef | classDefinition | varDef | functionDecl | varDecl )+;

// functions

functionBody: statement*;

statement: varDef | varDecl | assignment | functionCallStmt | ifStmt | whileStmt | returnSTMT | block | ';';

block: '{' statement* '}';

assignment: (memberAccess | OBJ_NAME) '=' value ';';

functionDef: OBJ_NAME OBJ_NAME args block;
functionDecl: OBJ_NAME OBJ_NAME args ';';
functionCallStmt: OBJ_NAME args ';';

args: '(' (arg (',' arg)*)? ')';
arg: OBJ_NAME '&'? OBJ_NAME;

// classes

classDefinition: 'class' OBJ_NAME (':' 'public' OBJ_NAME)? '{' 'public' ':' classBody '}' ';';

classBody: (fieldDecl | constructorDef | methodDef)*;

fieldDecl: OBJ_NAME OBJ_NAME ';';

constructorDef: OBJ_NAME '(' ')' '{' functionBody '}';

methodDef: 'virtual'? OBJ_NAME OBJ_NAME '(' ')' '{' functionBody '}';

// flow control

ifStmt: 'if' '(' value ')' statement ('else' statement)?;

whileStmt: 'while' '(' value ')' statement;

// variables

varDecl: OBJ_NAME '&'? OBJ_NAME ';';
varDef: OBJ_NAME '&'? OBJ_NAME '=' value ';';

value: logicalOr;

logicalOr: logicalAnd ('||' logicalAnd)*;

logicalAnd: comparison ('&&' comparison)*;

comparison: math (('==' | '<=' | '>=' | '!=' | '>' | '<') math)?;

memberAccess: OBJ_NAME '.' OBJ_NAME;

// literals

literal: STRING_LITERAL | intLiteral | boolLiteral;

intLiteral: NUM;

boolLiteral: 'true' | 'false';

// math (int)

math: multiplicative (('+' | '-') multiplicative)*;

multiplicative: unary (('*' | '/' | '%') unary)*;

unary: ('+' | '-' | '!')? primary;

primary: (functionCallExpr | intLiteral | boolLiteral | OBJ_NAME | memberAccess | '(' value ')');

functionCallExpr: OBJ_NAME args;

// return

returnSTMT: 'return' value? ';';

// lexer

LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
PREPROCESSOR: '#' ~[\r\n]* -> skip;

STRING_LITERAL: '"' (~["\\\r\n] | '\\' .)* '"';
NUM: [0-9]+;
OBJ_NAME: [a-zA-Z][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;
