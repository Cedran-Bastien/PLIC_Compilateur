package plic.repint;

public class Symbole {
    String type;
    int deplacement;

    public Symbole(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "\n\t\t\ttype='" + type + '\'' +
                ",\n\t\t\tdeplacement=" + deplacement +
                "\n\t\t}";
    }
}
