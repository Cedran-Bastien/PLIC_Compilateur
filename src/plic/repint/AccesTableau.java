package plic.repint;

import plic.Exeption.SemanticExeption;

public class AccesTableau extends Acces{
    String nom;

    Expression expression;

    public AccesTableau(String nom, Expression expression) {
        this.nom = nom;
        this.expression = expression;
    }

    @Override
    public void verifier() throws SemanticExeption {
        TDS.getInstance().identifier(new Entree(this.nom));
        this.expression.verifier();
    }

    @Override
    public String toMips() {
        return null;
    }

    @Override
    public String toString() {
        return "AccesTableau{" +
                "nom='" + nom + '\'' +
                ", expression=" + expression.toString() +
                '}';
    }
}
