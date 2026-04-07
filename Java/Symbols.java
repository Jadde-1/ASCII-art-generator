import java.awt.*;
import java.awt.image.BufferedImage;

public class Symbols {
    // Forskellige tegnsæt fra mørk til lys
    // Vi skal have flere symbols
    static String letters = "@#MBDOQUYXZmnhkdb][}{/|() Il!i;:,.";
    static String numbers  = "8963021745 ";
    static String mixed    = "@#8&963Mhdb!i;:,. ";
    static String full     = "@#$%&8BDMWZQOUYXmnhkdb][}{/|()Il!i;:~-,.";

    public static String getCharset() {
        return switch (Main.text) {
            case 1 -> letters;
            case 2 -> numbers;
            case 3 -> mixed;
            case 4 -> full;
            default -> full;
        };
    }

    public static char getChar(int brightness) {
        String charset = getCharset();
        // Vend brightness om så 0=lys tegn og 255=mørk tegn
        int invertedBrightness = 255 - brightness;
        int index = (int)((invertedBrightness / 255.0) * (charset.length() - 1));
        return charset.charAt(index);
    }

    public static BufferedImage toAscii(BufferedImage bufferedImage) {
        int imgW = bufferedImage.getWidth();
        int imgH = bufferedImage.getHeight();

        int charWidth  = 5 * Main.charScale;
        int charHeight = 6 * Main.charScale;
        int fontSize   = 6 * Main.charScale;

        BufferedImage asciiImg = new BufferedImage(imgW * charWidth, imgH * charHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D ag = asciiImg.createGraphics();

        ag.setColor(Color.WHITE);
       ag.fillRect(0, 0, asciiImg.getWidth(), asciiImg.getHeight());

        ag.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        ag.setColor(Color.BLACK);

        for (int y = 0; y < imgH; y++) {
            for (int x = 0; x < imgW; x++) {
                Color c = new Color(bufferedImage.getRGB(x, y));
                int brightness = 255 - c.getRed();
                char asciiChar = getChar(brightness);
                ag.drawString(String.valueOf(asciiChar), x * charWidth, y * charHeight + charHeight);
            }
        }

        ag.dispose();
        return asciiImg;
    }
}