package plic.tests;

import plic.Exeption.DoubleException;
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
            System.out.println(bloc.toString());
            System.out.println(TDS.getInstance());
        }catch (SyntaxeException syntaxeException){
            System.out.println("ERREUR: " + syntaxeException.message);
        }catch (DoubleException doubleException){
            System.out.println("ERREUR: " + doubleException.message);
        }
    }
}
