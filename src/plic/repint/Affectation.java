package plic.repint;

public class Affectation extends Instruction {
    Idf identifiant;
    Expression expression;

    public Affectation(Idf identifiant, Expression expression) {
        this.identifiant = identifiant;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "identifiant=" + identifiant.toString() +
                ", expression=" + expression.toString() +
                '}';
    }
}