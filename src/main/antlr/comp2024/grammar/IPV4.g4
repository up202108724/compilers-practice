grammar IPV4;

@header {
package pt.up.fe.comp2024;
}
// Defines any integer
INTEGER: [0-9]+;
// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;
// If IP is defined as a single token, we cannot check each number individually
// This way, each number will be a token
ip: INTEGER '.' INTEGER '.' INTEGER '.' INTEGER;
