package plic.tests;

import plic.Exeption.SyntaxeException;
import plic.analyse.AnalyseurLexical;
import plic.analyse.AnalyseurSynthaxique;

import java.io.File;
import java.io.FileNotFoundException;

public class TestScanner {
    public static void main(String[] args) throws FileNotFoundException, SyntaxeException {
        try {
            AnalyseurLexical analyseurLexical = new AnalyseurLexical(new File(args[0]));

            String next = analyseurLexical.next();

            while (!next.equals("EOF")){
                System.out.printf(next);
                next = analyseurLexical.next();
            }

        } catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found for Path : " + args[0]);
        }
    }
}
