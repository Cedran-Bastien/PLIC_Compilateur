package plic.repint;

public class Symbole {
    String type;
    int deplacement;
    int arrayLength;

    public Symbole(String type) {
        this.type = type;
    }

    public Symbole(String type, int length){
        this.type = type;
        this.arrayLength = length;
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "\n\t\t\ttype='" + type + '\'' +
                ",\n\t\t\tdeplacement=" + deplacement +
                "\n\t\t}";
    }
}
