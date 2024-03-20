package plic.repint;

import plic.Exeption.SemanticExeption;

import java.util.Arrays;

public class AccesTableau extends Idf{
    Expression expression;

    public AccesTableau(String nom, Expression expression) {
        super(nom);
        this.expression = expression;
    }

    @Override
    public void verifier() throws SemanticExeption {
        Symbole symbole = TDS.getInstance().identifier(new Entree(this.nom));
        if (!symbole.type.equals("tableau"))
            throw new SemanticExeption("variable " + this.nom + " must be 'tableau' type");
        this.expression.verifier();

        Expression precedante = this.expression;
        while (precedante instanceof AccesTableau) {
            precedante = ((AccesTableau) precedante).expression;
        }
    }

    @Override
    public String toMips() throws SemanticExeption {
        String subExpression = this.expression.toMips();
        if (this.expression instanceof Nombre){
            subExpression = "li $v0," + subExpression;
        }else {
            subExpression = subExpression + "lw $v0, ($a0)";
        }

        Symbole symbole = TDS.getInstance().identifier(new Entree(this.nom));


        return """
                %s
                
                li $a1, -4
                multu $v0, $a1
                mflo $v0 # 32 least significant bits of multiplication to $v0
                
                add $a0, $s7, %s
                add $a0, $a0, $v0
                """.formatted(subExpression, symbole.deplacement);

    }

    @Override
    public String toString() {
        return "AccesTableau{" +
                "nom='" + nom + '\'' +
                ", expression=" + expression.toString() +
                '}';
    }
}
