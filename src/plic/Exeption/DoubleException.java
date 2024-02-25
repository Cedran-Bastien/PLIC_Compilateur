package plic.Exeption;

public class DoubleException extends Exception{
    public String message;
    public DoubleException(String variableName){
        message = "Variable '" + variableName + "' is already declared";
    }
}
