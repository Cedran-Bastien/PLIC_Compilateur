package plic.tests;

import plic.Exeption.DoubleException;
import plic.Exeption.SemanticExeption;
import plic.Exeption.SyntaxeException;
import plic.analyse.AnalyseurSynthaxique;
import plic.repint.Bloc;
import plic.repint.TDS;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {
    public static void main(String[] args) {
        try {
            new Plic(args[0]);
        } catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found for Path : " + args[0]);
        }
    }

    Plic(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        AnalyseurSynthaxique analyseurSynthaxique = new AnalyseurSynthaxique(file);
        try {
            Bloc bloc = analyseurSynthaxique.analyse();
            bloc.verifier();
        }catch (SyntaxeException | DoubleException | SemanticExeption syntaxeException){
            System.out.println("ERREUR: " + syntaxeException.getMessage());
        }
    }
}
