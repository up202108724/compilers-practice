grammar Javamm;
@header {
package pt.up.fe.comp2024;
}
INTEGER : [0-9]+ ;
ID : [a-zA-Z_][a-zA-Z_0-9]* ;
WS : [ \t\n\r\f]+ -> skip ;

program
    : statement+ EOF
    ;
statement
    : expression ';' #ExprStmt
    | var=ID '=' value= expression ';' #Assignment
    ;
expression
    : '(' expression ')' #ParenthesizedExpr
    | expression op=('*' | '/') expression #BinaryOp
    | expression op=('+' | '-') expression #BinaryOp
    | expression op='<' expression #BinaryOp
    | expression op='&&' expression #BinaryOp
    | value=INTEGER #Integer
    | value=ID #Identifier
    ;