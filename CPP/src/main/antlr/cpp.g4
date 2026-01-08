grammar cpp;
start: stmt*;

stmt: (definition | declaration | ws)+;

definition: functionDef;
declaration: functionDecl;

// functions
functionBody: (functionCall | ';' | ws)+;

functionDef:  OBJ_NAME space OBJ_NAME args ws* (';' | '{' ws* functionBody ws* '}');
functionDecl: OBJ_NAME space OBJ_NAME args space ';';
functionCall: OBJ_NAME args;

args: '('(arg (optSpace ',' optSpace arg)* )? ')';
arg: (OBJ_NAME space OBJ_NAME);

// variables
//TODO

// operations
comparison: '==' | '=<' | '=>' | '>' | '<';
logicOp: '&&' | '||';
bitwiseOP: '|' | '&';

// literals
stringLiteral: '"' ('\\'?.) '"';

ws:(' ' | '\n');
space: ' '+;
optSpace: ' '*;

OBJ_NAME:[a-zA-Z][a-zA-Z0-9]*;
CHAR: [a-zA-Z0-9];
