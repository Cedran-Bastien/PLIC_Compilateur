package plic.analyse;

import plic.Exeption.DoubleException;
import plic.Exeption.SyntaxeException;
import plic.repint.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
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

    private void printError(String message) {
        System.out.println("ERREUR: "+message);
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
        if (!estIdf()){
            uniteCourante = analyseurLexical.next();
            throw new SyntaxeException("Identifier must not be 'programme' and should just contains lowercase and uppercase");
        }
        Idf acces = new Idf(uniteCourante);
        uniteCourante = analyseurLexical.next();
        return acces;
    }

    private boolean estIdf() {
        Pattern pattern = Pattern.compile("[a-z|A-Z]+");
        return pattern.matcher(uniteCourante).matches() && Arrays.stream(AnalyseurSynthaxique.RESERVE_WORD).noneMatch((item) -> Objects.equals(item, uniteCourante));
    }

    private void analyseBloc() throws SyntaxeException, DoubleException {
        // Bloc begin by '{'
        this.analyseTerminal(uniteCourante.equals("{"), "Top level bloc should begin by '{'");

        while (uniteCourante.equals("entier")){
            this.analyseDeclaration();
        }
        if (uniteCourante.equals("}")){
            throw new SyntaxeException("One instruction is asked");
        }else {
            this.programmeModel.ajouter(this.analyseInstruction());
            while (!uniteCourante.equals("}")){
                this.programmeModel.ajouter(this.analyseInstruction());
                // Bloc end by '}'
                this.analyseTerminal(!uniteCourante.equals("EOF"), "Top level bloc should end by '}'");
            }
        }
    }

    private void analyseDeclaration() throws SyntaxeException, DoubleException {
        Symbole symbole = new Symbole(uniteCourante);
        this.analyseType();
        Entree entree = new Entree(uniteCourante);
        this.analyseAcces();
        TDS.getInstance().ajouter(entree, symbole);
        this.analyseTerminal(uniteCourante.equals(";"), "missing character ';");
    }

    private void analyseType() throws SyntaxeException {
        this.analyseTerminal(uniteCourante.equals("entier"), "accepted type : 'entier'");
    }

    private Instruction analyseInstruction() throws SyntaxeException {
        try {
            return this.analyseES();
        } catch (SyntaxeException esException) {
            try {
                return this.analyseAffectation();
            } catch (SyntaxeException affectationException){
                throw new SyntaxeException("Instruction should be Es or Affectation : error : " + esException.message + " or " + affectationException.message);
            }

        }
    }

    private Instruction analyseAffectation() throws SyntaxeException{
        Idf idf = this.analyseAcces();
        this.analyseTerminal(uniteCourante.equals(":="), "accepted type : 'entier'");
        Expression expression = this.analyseExpression();
        this.checkEndInstructionDeclaration();
        return new Affectation(idf, expression);
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
                throw new SyntaxeException("'" + valueNotCst+ "' should be an integer or : " + syntaxeException.message);
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
