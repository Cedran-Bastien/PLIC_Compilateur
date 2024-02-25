package plic.repint;

public class Nombre extends Expression {
    int val;

    public Nombre(int value){
        this.val = value;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }
}
