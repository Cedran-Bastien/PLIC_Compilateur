package plic.repint;

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

    @Override
    public String toString() {
        return "Bloc{\n" +
                "instructions=" + this.instructions.stream().map((Object::toString)).collect(Collectors.joining("\n")) +
                "\n}";
    }
}
