package plic.repint;

import java.util.Objects;

public class Entree {
    String idf;

    public Entree(String identifiant) {
        this.idf = identifiant;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entree entree = (Entree) o;
        return Objects.equals(idf, entree.idf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idf);
    }
}
