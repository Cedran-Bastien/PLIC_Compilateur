package plic.analyse;

import plic.Exeption.DoubleException;
import plic.Exeption.SyntaxeException;
import plic.repint.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class AnalyseurSynthaxique {
    private AnalyseurLexical analyseurLexical;
    private String uniteCourante;

    private Bloc programmeModel;

    final static String[] RESERVE_WORD = new String[]{"programme", "ecrire", "entier"};

    public AnalyseurSynthaxique(File file) throws FileNotFoundException {
        analyseurLexical = new AnalyseurLexical(file);
    }

    public Bloc analyse() throws SyntaxeException, DoubleException {
        this.programmeModel = new Bloc();
        uniteCourante = analyseurLexical.next();
        this.analyseProg();
        this.analyseTerminal(!uniteCourante.equals("EOF"),"Only one first level bloc allowed");

        return this.programmeModel;
    }

    private void analyseTerminal(Boolean b, String messageError) throws SyntaxeException {
        if (!b){
            throw new SyntaxeException(messageError);
        }
        uniteCourante = analyseurLexical.next();
    }

    private void analyseProg() throws SyntaxeException, DoubleException {
        // Check begin by 'programme'
        this.analyseTerminal(uniteCourante.equals("programme"), "Must begin by 'programme' ");
        if (uniteCourante.equals("EOF")){
            throw new SyntaxeException("Wrong program declaration");
        }
        // Check right idf
        this.analyseAcces();
        if (uniteCourante.equals("EOF")){
            throw new SyntaxeException("Wrong program declaration");
        }

        this.analyseBloc();
    }

    private Idf analyseAcces() throws SyntaxeException{
        String nom = uniteCourante;
        Idf acces = new Idf(nom);
        if (estIdf()){
            uniteCourante = this.analyseurLexical.next();
            if (uniteCourante.equals("[")){
                uniteCourante = this.analyseurLexical.next();
                Expression expression = this.analyseExpression();
                analyseTerminal(uniteCourante.equals("]"), "missing character ']'");
                acces = new AccesTableau(nom, expression);
            }
        }else {
            throw new SyntaxeException("Identifier missing - identifier must not be 'programme' and should just contains lowercase and uppercase");
        }

        return acces;
    }

    private boolean estIdf() {
        Pattern pattern = Pattern.compile("[a-z|A-Z]+");
        return pattern.matcher(uniteCourante).matches() && Arrays.stream(AnalyseurSynthaxique.RESERVE_WORD).noneMatch((item) -> Objects.equals(item, uniteCourante));
    }

    private boolean estType() {
        return uniteCourante.equals("entier") | uniteCourante.equals("tableau");
    }

    private void analyseBloc() throws SyntaxeException, DoubleException {
        // Bloc begin by '{'
        this.analyseTerminal(uniteCourante.equals("{"), "Top level bloc should begin by '{'");

        while (uniteCourante.equals("entier") || uniteCourante.equals("tableau")){
            this.analyseDeclaration();
        }
        if (uniteCourante.equals("}")){
            throw new SyntaxeException("One instruction is asked");
        }else {
            this.programmeModel.ajouter(this.analyseInstruction());
            while (!uniteCourante.equals("}")){
                this.programmeModel.ajouter(this.analyseInstruction());
                // Bloc end by '}'
                if (uniteCourante.equals("EOF")) throw new SyntaxeException("Top level bloc should end by '}'");
            }
        }
    }

    private void analyseDeclaration() throws SyntaxeException, DoubleException {
        this.analyseType();
        Symbole symbole = null;
        if (uniteCourante.equals("tableau")){
             symbole = analyseTableau();
        }else {
            symbole = new Symbole(uniteCourante);
            uniteCourante = this.analyseurLexical.next();
        }
        Entree entree = new Entree(uniteCourante);
        TDS.getInstance().ajouter(entree, symbole);
        uniteCourante = this.analyseurLexical.next();
        this.analyseTerminal(uniteCourante.equals(";"), "missing character ';'");
    }

    private void analyseType() throws SyntaxeException {
        if (!uniteCourante.equals("entier") && !uniteCourante.equals("tableau")){
            throw new SyntaxeException("Declaration must begin by 'entier' ou 'tableau'");
        }
    }

    private Symbole analyseTableau() throws SyntaxeException {
        String type = uniteCourante;
        uniteCourante = analyseurLexical.next();
        analyseTerminal(uniteCourante.equals("["), "Array declaration must have '[Int value]'");
        if (!estCsteEntier() || uniteCourante == null){
            throw new SyntaxeException("'" + uniteCourante + "' is not an integer");
        }
        Symbole res = new Symbole(type, Integer.parseInt(uniteCourante));
        uniteCourante = analyseurLexical.next();
        analyseTerminal(uniteCourante.equals("]"), "Array declaration must have '[Int value]'");

        return res;
    }

    private Instruction analyseInstruction() throws SyntaxeException {
        try {
            return this.analyseES();
        } catch (SyntaxeException esException) {
            try {
                return this.analyseAffectation();
            } catch (SyntaxeException affectationException){
                throw new SyntaxeException("Instruction should be Es or Affectation : error : " + esException.getMessage() + " or " + affectationException.getMessage());
            }

        }
    }

    private Instruction analyseAffectation() throws SyntaxeException{
        Idf acces = this.analyseAcces();
        this.analyseTerminal(uniteCourante.equals(":="), "accepted type : 'entier'");
        Expression expression = this.analyseExpression();
        this.checkEndInstructionDeclaration();
        return new Affectation(acces, expression);
    }

    private void checkEndInstructionDeclaration() throws SyntaxeException {
        this.analyseTerminal(uniteCourante.equals(";"), "missing character ';'");
    }

    private Ecrire analyseES() throws SyntaxeException {
        analyseTerminal(uniteCourante.equals("ecrire"), "Character '" + uniteCourante + "' not allowed here. Instruction must begin by 'ecrire', at list one instruction asked minimum.");
        Expression expression = this.analyseExpression();
        this.checkEndInstructionDeclaration();
        return new Ecrire(expression);
    }

    private Expression analyseExpression() throws SyntaxeException {
        return this.analyseOperande();
    }

    private Expression analyseOperande() throws SyntaxeException {
        if (!estCsteEntier()){
            String valueNotCst = uniteCourante;
            try {
                return this.analyseAcces();
            } catch (SyntaxeException syntaxeException) {
                throw new SyntaxeException("'" + valueNotCst+ "' should be an integer or : ");
            }
        } else {
            Expression cst = new Nombre(Integer.parseInt(uniteCourante));
            uniteCourante = this.analyseurLexical.next();
            return cst;
        }
    }

    private boolean estCsteEntier() {
        Pattern pattern = Pattern.compile("^-\\d+$|^\\d+$");
        return pattern.matcher(uniteCourante).matches();
    }
}
