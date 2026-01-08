grammar cpp;
start: stmt*;

stmt: ';';

comparison: '==' | '=<' | '=>' | '>' | '<';
logicOp: '&&' | '||';
bitwiseOP: '|' | '&';