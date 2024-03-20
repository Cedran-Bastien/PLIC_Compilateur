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

    public String toMips() throws SemanticExeption {

        String mipsCode = "";
        for (Instruction i : this.instructions){
            mipsCode += i.toMips() + "\n";
        }

        int spaceToSave  = TDS.getInstance().dptDepl;
        return ".data\n" +
                "linebreak: \t.asciiz \"\\n\"\n" +
                "tabOutOfRangeIndex: .asciiz  \"ERREUR: indice out of range\"\n" +
                ".text\n" +
                "main:\n" +
                "move $s7,$sp\n" +
                "add $sp,$sp, " + spaceToSave + "\n" +
                mipsCode + """
                Exit:
                    li $v0, 4 	# $v0 <- code du print
                	syscall 	# afficher
                    li $v0, 10
                    syscall
                EndIf:
              
                """;

    }

    @Override
    public String toString() {
        return "Bloc{\n" +
                "instructions=" + this.instructions.stream().map((Object::toString)).collect(Collectors.joining("\n")) +
                "\n}";
    }
}
