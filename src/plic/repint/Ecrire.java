package plic.repint;

import plic.Exeption.SemanticExeption;

public class Ecrire extends Instruction {
    Expression expression;

    public Ecrire(Expression expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "\n\texpression=" + expression.toString() +
                '}';
    }

    @Override
    public void verifier() throws SemanticExeption {
        this.expression.verifier();
    }
}
