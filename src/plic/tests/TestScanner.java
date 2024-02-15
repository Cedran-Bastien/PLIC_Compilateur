package plic.tests;

import plic.analyse.AnalyseurLexical;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestScanner {
    public static void main(String[] args) throws FileNotFoundException, EOFException {
        File f = new File("/home/cedran/Documents/Etude/S6/Compilation/Plic/src/plic/sources/t1.plic");
        AnalyseurLexical sc = new AnalyseurLexical(f);

        String value = sc.next();
        while (!value.equals("EOF")){
            System.out.println(value);
            value = sc.next();
        }
        System.out.println(value);
    }
}
