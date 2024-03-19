package plic.repint;

import plic.Exeption.SemanticExeption;

public class Affectation extends Instruction {
    Acces identifiant;
    Expression expression;

    public Affectation(Acces identifiant, Expression expression) {
        this.identifiant = identifiant;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "\n\tidentifiant=" + identifiant.toString() +
                ", \n\texpression=" + expression.toString() +
                "\n}";
    }

    @Override
    public void verifier() throws SemanticExeption {
        this.identifiant.verifier();
        this.expression.verifier();
    }

    @Override
    public String toMips() {
        String value = this.expression.toMips();
        String emplacementMemoire = this.identifiant.toMips();
        return "li $v0, " + value + "\n" +
                "sw $v0, " + emplacementMemoire;
    }
}
