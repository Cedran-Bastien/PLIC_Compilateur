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

//    move $a0, $v0
    @Override
    public String toMips() throws SemanticExeption {
        String printValue = this.expression.toMips();
        if (this.expression instanceof Nombre){
            printValue = """
                    li $v0, %s
                    
                    """.formatted(printValue);
        }else {
            printValue = """
                    %s
                    lw $v0, ($a0)
                    """.formatted(printValue);
        }
        return """
                %s
                move $a0, $v0
                li $v0, 1
                syscall

                # ecrire linebreak
                la $a0, linebreak
                li $v0, 4
                syscall""".formatted(printValue);
    }
}
