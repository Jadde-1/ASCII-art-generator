import java.awt.*;
import java.awt.image.BufferedImage;

// Skal scalere bileldet ned kun hvis det er for stort så det ikke bliver for tungt eller for kompleksted.
// Man kunne implamentere en scalerinbgs faktor alt efter hvor stor biledet er så flere eller færre detaljer
public class Downscaler {
    // Vi henter vore billede scale fra main så kan man vægle størrelse


    public static BufferedImage down(BufferedImage bufferedImage,double scale) {
        int newWidth = (int) (bufferedImage.getWidth() / scale);
        int newHeight = (int) (bufferedImage.getHeight() / scale);
        //return bufferedImage;

        BufferedImage NewImg = new BufferedImage(newWidth, newHeight, bufferedImage.getType());

        Graphics2D g = NewImg.createGraphics();
        g.drawImage(bufferedImage, 0,0,newWidth,newHeight,null);
        g.dispose();

        return NewImg;
    }


}

//Soruces
// 1. https://www.baeldung.com/java-resize-image