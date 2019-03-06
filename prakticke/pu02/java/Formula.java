import java.util.*;

public class Formula {
    public Formula(){}
    public Formula[] subf(){
        return new Formula[0];
    }
    public String toString(){
        return "";
    }
    public boolean isSatisfied(Map<String, Boolean> v){return true;}
    public boolean equals(Formula other){
        return this.equals(other);
    }
    public int deg(){
        return 0;
    }
    public Set<String> vars(){
        Set<String> res = new HashSet<String>();
        String s = this.toString();
        for (int i = 0; i < s.length(); i ++){
            if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')){
                res.add(""+s.charAt(i));
            }
        }
        return res;
    }
}

class Variable extends Formula{
    public String name;
    public Variable(String name){
        this.name = name;
    }
    public String name() {
    	meno = this.name;
        return meno;
    }
    public boolean equals(Formula other){
    	boolean rovnost = this.toString().equals(other.toString());
        return rovnost;
    }
    public String toString(){
    	meno = this.name;
        return meno;
    }
    public int deg(){
        return 0;
    }
    public Set<String> vars(){
        Set<String> res = new HashSet<String>();
        res.add(name);
        return res;
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        return v.get(name());
    }
    public Formula[] subf() {
        return new Formula[0];
    }
}

class Negation extends Formula{
    public Formula originalFormula;
    public Negation(Formula originalFormula){
        this.originalFormula = originalFormula;
    }
    public Formula originalFormula(){
        return this.originalFormula;
    }
    public String toString(){
        return "-" + this.originalFormula;
    }
    public boolean equals(Formula other){
        return this.toString().equals(other.toString());
    }
    public int deg(){
        return originalFormula.deg() + 1;
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        return !originalFormula.isSatisfied(v);
    }
    public Formula[] subf() {
        return originalFormula.subf();
    }
}

class Disjunction extends Formula{
    public Formula[] disjuncts;
    public Disjunction(Formula[] disjuncts){
        this.disjuncts  = disjuncts.clone();
    }
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("(");
        for (int i = 0; i < disjuncts.length; i ++){
            out.append(disjuncts[i]);
            out.append("|");
        }
        return out.toString().substring(0, out.length()-1) + ")";
    }
    public boolean equals(Formula other){
        return this.toString().equals(other.toString());
    }
    public int deg(){
        int res = 0;
        for (int i = 0; i < disjuncts.length; i ++){
            res += disjuncts[i].deg();
        }
        return res + 1;
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        Boolean res = false;
        for (int i = 0; i < disjuncts.length; i++) {
            res |= disjuncts[i].isSatisfied(v);
        }
        return res;
    }

    public Formula[] subf() {
        return disjuncts;
    }
}

class Conjunction extends Formula{
    public Formula[] conjuncts;
    public Conjunction(Formula[]conjuncts){
        this.conjuncts = conjuncts.clone();
    }
    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("(");
        for (int i = 0; i < conjuncts.length; i ++){
            out.append(conjuncts[i]);
            out.append("&");
        }
        return out.toString().substring(0, out.length()-1) + ")";
    }
    public boolean equals(Formula other){
        return this.toString().equals(other.toString());
    }
    public int deg(){
        int res = 0;
        for (int i = 0; i < conjuncts.length; i ++){
            res += conjuncts[i].deg();
        }
        return res + 1;
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        boolean res = true;
        for (int i = 0; i < conjuncts.length; i++){
            res &= conjuncts[i].isSatisfied(v);
        }
        return res;
    }
    public Formula[] subf() {
        return conjuncts;
    }
}

class BinaryFormula extends Formula{
    public Formula leftSide; Formula rightSide;
    public BinaryFormula(){}
    public BinaryFormula(Formula leftSide, Formula rightSide){
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }
    public Formula leftSide(){
        return this.leftSide;
    }
    public Formula rightSide(){
        return this.rightSide;
    }
    public boolean equals(Formula other){
        return this.toString().equals(other.toString());
    }
    public int deg(){
        return leftSide.deg() + rightSide.deg() + 1;
    }
    public Formula[] subf() {
        Formula[] res = {this.leftSide, this.rightSide};
        return res;
    }
}

class Implication extends BinaryFormula{
    Implication(Formula left, Formula right) {
        super(left, right);
    }
    public String toString(){
        return "(" + this.leftSide + "->" + this.rightSide + ")";
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        if (leftSide.isSatisfied(v) && !rightSide.isSatisfied(v)){
            return false;
        }
        return true;
    }
}


class Equivalence extends BinaryFormula{
    Equivalence(Formula left, Formula right) {
        super(left, right);
    }
    public String toString(){
        return "(" + this.leftSide + "<->" + this.rightSide + ")";
    }
    public boolean isSatisfied(Map<String, Boolean> v) {
        if (leftSide.isSatisfied(v) != rightSide.isSatisfied(v)) return false;
        return true;
    }
}