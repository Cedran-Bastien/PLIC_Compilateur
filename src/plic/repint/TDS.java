package plic.repint;

import plic.Exeption.DoubleException;
import plic.Exeption.SemanticExeption;

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
        if (TDS.tds != null) {
            return tds;
        }
        TDS.tds = new TDS();
        return TDS.tds;
    }

    public void ajouter(Entree entree, Symbole symbole) throws DoubleException {
        if (this.declarationRequirement.keySet().stream().anyMatch((key) -> key.idf.equals(entree.idf))){
            throw new DoubleException(entree.idf);
        }

        // Set deplacement
        symbole.deplacement = dptDepl;
        this.dptDepl -= symbole.type.equals("tableau") ? symbole.arrayLength * 4 : 4;


        this.declarationRequirement.put(entree, symbole);
        System.out.println();
    }

    public Map<Entree, Symbole> getDeclarationRequirement() {
        return declarationRequirement;
    }

    public Symbole identifier(Entree entree) throws SemanticExeption {
        Symbole res = this.declarationRequirement.get(entree);
        if (res == null){
            throw new SemanticExeption("'" + entree.idf + "' is not declared");
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "TDS{" +
                "\n\tdptDepl=" + dptDepl +
                ", \n\tdeclarationRequirement= {";
        for (Entree entree : this.declarationRequirement.keySet()){
            res += "\n\t\tentree=" + entree +
                    "\n\t\tSymbole=" + this.declarationRequirement.get(entree).toString();
        }
        return res + "\n\t}\n}";
    }
}
