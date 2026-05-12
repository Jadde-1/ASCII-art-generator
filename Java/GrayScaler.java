import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayScaler {
    public static BufferedImage Gray(BufferedImage bufferedImage) {
        int imgwidth = bufferedImage.getWidth();
        int imgheight = bufferedImage.getHeight();

        for (int i=0;i<imgheight;i++){
            for (int j=0;j<imgwidth;j++){
                Color c = new Color(bufferedImage.getRGB(j,i));
                float[] hsb = Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
                Color newColor = new Color(Color.HSBtoRGB(hsb[0],0,hsb[2]));
                bufferedImage.setRGB(j,i,newColor.getRGB());
            }
        }
        return bufferedImage;
    }
}
