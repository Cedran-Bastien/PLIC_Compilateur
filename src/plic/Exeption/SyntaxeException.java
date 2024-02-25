package plic.Exeption;

public class SyntaxeException extends Exception{
    public String message;
    public SyntaxeException(String m){
        message = m;
    }
}
