package plic.tests;

import plic.Exeption.DoubleException;
import plic.Exeption.SemanticExeption;
import plic.Exeption.SyntaxeException;
import plic.analyse.AnalyseurSynthaxique;
import plic.repint.Bloc;
import plic.repint.TDS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Plic {
    public static void main(String[] args) {
        try {
//            File file = new File(args[0]);
//            for (String file1 : file.list()){
//                File file2 = new File(file1);
//            }
//            AnalyseurSynthaxique analyseurSynthaxique = new AnalyseurSynthaxique(file);
//            try {
//                Bloc bloc = analyseurSynthaxique.analyse();
//                bloc.verifier();
//            }catch (SyntaxeException | DoubleException | SemanticExeption syntaxeException){
//                System.out.println("ERREUR: " + syntaxeException.getMessage());
//            }
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
            System.out.println(bloc);
//            System.out.println(bloc.toMips());
        }catch (SyntaxeException | DoubleException | SemanticExeption syntaxeException){
            System.out.println("ERREUR: " + syntaxeException.getMessage());
        }
    }
}
