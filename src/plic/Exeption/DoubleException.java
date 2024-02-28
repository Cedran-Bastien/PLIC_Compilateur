package plic.Exeption;

public class DoubleException extends Exception{
    public DoubleException(String variableName){
        super("Variable '" + variableName + "' is already declared");
    }
}
