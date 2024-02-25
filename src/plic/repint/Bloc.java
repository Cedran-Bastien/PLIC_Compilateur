package plic.repint;

import java.util.LinkedList;
import java.util.List;

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
        return this.instructions.stream().map((Object::toString)).toString();
    }
}
