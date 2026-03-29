//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // scaler billedet mere eller mindre
    // 0 er normal size
    // Minus ganger op
    // Plus dividere
    public static double scale = 8;

    // Jo færre farver jo lavere
    public static int bitAmount = 24;

    // Vælg pack af tegn
    public static int text = 4;

    // Grey Yes = 1 else no
    public static int grey = 1;

    // 1=ascii 0=normalt billede
    public static int ascii = 1;

    // Gør de brugte tegn større og mindre
    public static int charScale = 1;

    public static void main(String[] args) {
        new win();
    }
}