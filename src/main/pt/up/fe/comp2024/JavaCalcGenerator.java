package pt.up.fe.comp2024;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
public class JavaCalcGenerator extends AJmmVisitor<String,String> {

    private String classname;

    public JavaCalcGenerator(String Classname){
        this.classname= Classname;
    }
    protected void buildVisitor(){
        addVisit("Program", this::dealWithProgram);
        addVisit("Assignment", this::dealWithAssignment);
        addVisit("Integer", this::dealWithLiteral);
        addVisit("Identifier", this::dealWithIdentifier);
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
        return ret;
    }
    private String dealWithAssignment(JmmNode jmmNode, String s){
        return s + "int " + jmmNode.get("var") + " = " + jmmNode.get("value") + ";";
    }
    private String dealWithLiteral(JmmNode jmmNode, String s ){
        return jmmNode.get("value");
    }
    private String dealWithIdentifier(JmmNode jmmNode, String s){
        return jmmNode.get("var");
    }
}
