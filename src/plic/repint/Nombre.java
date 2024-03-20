package plic.repint;

import plic.Exeption.SemanticExeption;

public class Nombre extends Expression {
    int val;

    public Nombre(int value){
        this.val = value;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }

    @Override
    public void verifier() throws SemanticExeption {}

    @Override
    public String toMips() {
        return """
                %s\n
                """.formatted(String.valueOf(val));
    }

    public int getVal() {
        return val;
    }
}
