package plic.repint;

import plic.Exeption.SemanticExeption;

public abstract class Instruction {
    public abstract void verifier() throws SemanticExeption;

    public abstract String toMips() throws SemanticExeption;
}
