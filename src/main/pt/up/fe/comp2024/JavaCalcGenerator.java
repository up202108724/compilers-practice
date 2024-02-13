package pt.up.fe.comp2024;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;

import java.util.*;

public class JavaCalcGenerator extends AJmmVisitor<String,String> {

    private String classname;
    private Map<String,Integer> writes;
    private Map<String,Integer> reads;
    public JavaCalcGenerator(String Classname){
        this.classname= Classname;
        this.writes= new HashMap<String, Integer>();
        this.reads = new HashMap<String,Integer>();
    }
    protected void buildVisitor(){
        addVisit("Program", this::dealWithProgram);
        addVisit("Assignment", this::dealWithAssignment);
        addVisit("ExprStmt", this::dealWithExpression);
        addVisit("Integer", this::dealWithLiteral);
        addVisit("Identifier", this::dealWithLiteral);
        addVisit("ParenthesizedExpr", this::dealWithParenthesis);
        addVisit("BinaryOp", this::dealWithBinaryOp);
    }
    private String dealWithProgram(JmmNode jmmNode, String s){
        s = (s!=null? s : "");
        String ret = s + "public class" + this.classname + "{\n";
        String s2 = s + "\t";
        ret += s2 + "public class void main(String [] args)" + "{\n";

        for( JmmNode child : jmmNode.getChildren()){
            ret += visit(child, s2 + "\t");
            ret += "\n";
        }
        ret += s2 + "}\n";
        ret += s + "}\n";
        Set<String> keys = new HashSet<>(reads.keySet());
        keys.addAll(writes.keySet());
        for (String key : keys){
            int read = reads.getOrDefault(key,0);
            int write = writes.getOrDefault(key,0);
            System.out.println("\"" + write + "writes" + "and " + read + "reads " + "\"" );
        }
        return ret;
    }
    private String dealWithExpression(JmmNode jmmNode, String s){
        String ret="";
        for (JmmNode child : jmmNode.getChildren()){
            ret += visit(child, "");

        }
        return s + "System.out.println(" + ret + ");";
    }
    private String dealWithAssignment(JmmNode jmmNode, String s){
        writes.put(jmmNode.get("var"), writes.getOrDefault(jmmNode.get("var"),0)+1);
        return s + "int " + jmmNode.get("var") + " = " + visit(jmmNode.getJmmChild(0),"") + ";";

    }
    private String dealWithLiteral(JmmNode jmmNode, String s ){

        return jmmNode.get("value");
    }
    private String dealWithBinaryOp(JmmNode jmmNode, String s){ return visit(jmmNode.getJmmChild(0),s) + jmmNode.get("op") + visit(jmmNode.getJmmChild(1),s);}
    private String dealWithParenthesis(JmmNode jmmNode, String s){
        return s + "(" + visit(jmmNode.getJmmChild(0),"") + ")";
    }
}


