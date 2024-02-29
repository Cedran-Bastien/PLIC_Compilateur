package plic.repint;

import plic.Exeption.SemanticExeption;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
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

    public String toMips() {
        String mipsCode = this.instructions.stream()
                .map(Instruction::toMips)
                .collect(Collectors.joining("\n"));


        int nbVariable = TDS.getInstance().declarationRequirement.size();
        int spaceToSave = nbVariable*4;
        return ".data\n" +
                "linebreak: \t.asciiz \"\\n\"\n" +
                ".text\n" +
                "main:\n" +
                "move $s7,$sp\n" +
                "add $sp,$sp,-" + spaceToSave + "\n" +
                mipsCode;

    }

    @Override
    public String toString() {
        return "Bloc{\n" +
                "instructions=" + this.instructions.stream().map((Object::toString)).collect(Collectors.joining("\n")) +
                "\n}";
    }
}
