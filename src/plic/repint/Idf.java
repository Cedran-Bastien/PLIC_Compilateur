package plic.repint;

import plic.Exeption.SemanticExeption;

public class Idf extends Expression{
    String nom;

    public Idf(String name) {
        this.nom = name;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    @Override
    public void verifier() throws SemanticExeption {
        TDS.getInstance().identifier(new Entree(this.nom));
    }
}
