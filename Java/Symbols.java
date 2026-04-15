import java.awt.*;
import java.awt.image.BufferedImage;

public class Symbols {
    static String letters = "@#MBDOQUYXZmnhkdb][}{/|() Il!i;:,.";
    static String numbers = "8963021745 ";
    static String mixed = "@#8&963Mhdb!i;:,. ";
    static String full = "@#$%&8BDMWZQOUYXmnhkdb][}{/|()Il!i;:~-,.\u200E ";
    static String blocks = "█ ▌▀■▄ \u200E";
    static String rain = "▓ ▒ ░";

    static String fontName = "Monospaced";
    static int fontStyle = Font.PLAIN;

    public static void setFont(String name, int style) {
        fontName = name;
        fontStyle = style;
    }

    public static String getCharset() {
        return switch (Main.text) {
            case 1 -> letters;
            case 2 -> numbers;
            case 3 -> mixed;
            case 4 -> full;
            case 5 -> blocks;
            case 6 -> rain;
            default -> full;
        };
    }

    public static char getChar(int brightness) {
        String charset = getCharset();
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
        // Vi har her mulighed for at ændre font name og stil og størrelse
        ag.setFont(new Font(fontName, fontStyle, fontSize));
        ag.setColor(new Color(190, 149, 70));

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