package plic.repint;

import plic.Exeption.SemanticExeption;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Bloc {
    List<Instruction> instructions;

    public Bloc() {
        instructions = new LinkedList<>();
    }

    public void ajouter(Instruction instruction){
        this.instructions.add(instruction);
    }

    public void verifier() throws SemanticExeption {
        for (Instruction instruction : this.instructions) {
            instruction.verifier();
        }
    }

    @Override
    public String toString() {
        return "Bloc{\n" +
                "instructions=" + this.instructions.stream().map((Object::toString)).collect(Collectors.joining("\n")) +
                "\n}";
    }
}
