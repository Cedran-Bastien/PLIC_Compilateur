package plic.repint;

import plic.Exeption.SemanticExeption;

public abstract class Expression {
    public abstract void verifier() throws SemanticExeption;

    public abstract String toMips() throws SemanticExeption;
}
