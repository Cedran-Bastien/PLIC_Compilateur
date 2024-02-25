package plic.repint;

public class Ecrire extends Instruction {
    Expression expression;

    public Ecrire(Expression expression){
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "expression=" + expression.toString() +
                '}';
    }
}
