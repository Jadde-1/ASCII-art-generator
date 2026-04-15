import java.awt.*;
import java.awt.image.BufferedImage;

public class Quantization {
    public static BufferedImage color(BufferedImage bufferedImage, int bitAmount) {
        int imgwidth = bufferedImage.getWidth();
        int imgheight = bufferedImage.getHeight();

        for (int i = 0; i < imgheight; i++) {
            for (int j = 0; j < imgwidth; j++) {
                Color c = new Color(bufferedImage.getRGB(j, i));

                int qred = (int)(Math.floor(c.getRed() * (bitAmount / 256.0)));
                int qgreen = (int)(Math.floor(c.getGreen() * (bitAmount / 256.0)));
                int qblue = (int)(Math.floor(c.getBlue() * (bitAmount / 256.0)));

                qred = (int)(qred * (255.0 / (bitAmount - 1)));
                qgreen = (int)(qgreen * (255.0 / (bitAmount - 1)));
                qblue = (int)(qblue * (255.0 / (bitAmount - 1)));

                Color newColor = new Color(qred, qgreen, qblue);
                bufferedImage.setRGB(j, i, newColor.getRGB());
            }
        }

        return bufferedImage;
    }
}

// KODEN SKAL OPDATERES den kan ikke vælge antqal af faktiske farver kun bit 2^n i geuss

// Soruces
// 1. https://en.wikipedia.org/wiki/Color_quantization