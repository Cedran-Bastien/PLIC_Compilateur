package plic.repint;

import plic.Exeption.DoubleException;

import java.util.HashMap;
import java.util.Map;

public class TDS {
    private static TDS tds;
    protected int dptDepl;
    protected Map<Entree, Symbole> declarationRequirement;

    private TDS() {
        this.dptDepl = 0;
        this.declarationRequirement = new HashMap<>();
    }

    public static TDS getInstance(){
        if (tds != null) {
            return tds;
        }
        return new TDS();
    }

    public void ajouter(Entree entree, Symbole symbole) throws DoubleException {
        if (this.declarationRequirement.keySet().stream().anyMatch((key) -> key.idf.equals(entree.idf))){
            throw new DoubleException(entree.idf);
        }

        // Set deplacement
        symbole.deplacement = dptDepl;
        this.dptDepl -= 4;

        this.declarationRequirement.put(entree, symbole);
    }

    public Map<Entree, Symbole> getDeclarationRequirement() {
        return declarationRequirement;
    }
}
