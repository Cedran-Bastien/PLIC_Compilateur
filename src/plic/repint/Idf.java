package plic.repint;

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
}
