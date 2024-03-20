package plic.repint;

import plic.Exeption.SemanticExeption;

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
    public String toMips() throws SemanticExeption {
        Symbole symbole = TDS.getInstance().identifier(new Entree(this.identifiant.nom));
        String value = this.expression.toMips();
        if (this.expression instanceof Nombre){
            value = "li $v0," + value;
        }else {
            value = value + "\nlw $v0, ($a0)";
         }

        String checkArrayIndice = """
                la $a0, tabOutOfRangeIndex
                li $v1, 0
                ble $v0, $v1, Exit
                
                li $v1, %s
                bgt $v0, $v1, Exit
                """.formatted(symbole.arrayLength);
        if (!(this.expression instanceof AccesTableau))
            checkArrayIndice ="";

        String emplacementMemoire = this.identifiant.toMips();
        return """
                %s
                
                %s
                
                sw $v0, ($sp)
                add $sp,$sp,-4
                
                %s
                
                add $sp,$sp,4
                lw $v0, ($sp)
                
                sw $v0, ($a0)
                """.formatted(value, checkArrayIndice, emplacementMemoire);
    }
}
