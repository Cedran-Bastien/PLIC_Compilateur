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

    @Override
    public String toMips() {
        String printValue = this.expression.toMips();
        return """
                lw $v0, %s
                move $a0, $v0
                li $v0, 1
                syscall

                # ecrire linebreak
                la $a0, linebreak
                li $v0, 4
                syscall""".formatted(printValue);
    }
}
