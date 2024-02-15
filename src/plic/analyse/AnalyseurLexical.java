package plic.analyse;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyseurLexical {
    Scanner sc;
    public AnalyseurLexical(File file) throws FileNotFoundException {
        sc = new Scanner(file);
    }

    public String next() throws EOFException {
        if (!sc.hasNext()){
            sc.close();
            return "EOF";
        }

        String next = sc.next();
        if (next.contains("//")){
            sc.nextLine();
            return this.next();
        }

        return next;

    }
}
