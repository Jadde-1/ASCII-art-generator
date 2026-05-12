public class Main {
    // Ny update kan du scaleres ned nu. Simplificeret downscaler.java
    public static double scale = 20;

    // Jo færre farver jo lavere
    public static int bitAmount = 24;

    // Vælg pack af tegn (1-6)
    public static int text = 1;

    // Gråskala til/fra
    public static boolean grey = true;

    // ASCII konvertering til/fra
    public static boolean ascii = true;

    // Gør de brugte tegn større og mindre
    public static int charScale = 3;

    public static void main(String[] args) {
        new win();
    }
}




