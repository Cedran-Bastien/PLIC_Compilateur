package plic.analyse;

import plic.Exeption.SyntaxeException;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AnalyseurLexical {
    Scanner sc;
    public AnalyseurLexical(File file) throws FileNotFoundException {
        sc = new Scanner(file);
    }

    public String next(){
        if (!sc.hasNext()){
            sc.close();
            return "EOF";
        }

        String next = sc.next();
        if (next.contains("//")){
            sc.nextLine();
            next = this.next();
        }




        return next;

    }
}
