import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class win extends JPanel{
    int height = 1000;
    int width = 800;

    public int imgwidth;
    public int imgheight;

    String imgpath = "C:\\Github\\ASCII-art-generator\\Java\\TEST-IMG.jpg";
    // Jasper path: "C:\\Github\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // J path 2: "C:\\Users\\Vrill\\Documents\\GitHub\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // Malik path: "C:\\Users\\malik\\IdeaProjects\\ASCII\\src\\L-1253-00-000003-wpu.jpg"
    win(){
        JFrame mainFrame = new JFrame("ASCII Art Generator");
        mainFrame.setPreferredSize(new Dimension(width,height));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(this);
        mainFrame.setVisible(true);

        JLabel l = new JLabel("test label");

        //Buttons
        JButton b = new JButton("Toggle ASCII");

        mainFrame.pack();

        JPanel border = new JPanel(new BorderLayout());

        //border.add(mainFrame, BorderLayout.WEST);
        border.add(b, BorderLayout.EAST);

    }

    public void paint(Graphics g) {
        Image img = null;
        try {
            img = createImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(img, (getWidth()/2)-(imgwidth/2),(getHeight()/2)-(imgheight/2),this);
    }

    private Image createImage() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imgpath));
        System.out.println(bufferedImage);
        imgwidth = bufferedImage.getWidth();
        imgheight = bufferedImage.getHeight();
        //Vi scalere billedet til størrelse
        bufferedImage = Downscaler.down(bufferedImage, Main.scale);

        // Vi ændre farve til sort-hvis
        if (Main.grey == 1) {
            bufferedImage = GrayScaler.Gray(bufferedImage);
        }

        // Minsker antallet af farver.(i bit form)
        bufferedImage = Quantization.color(bufferedImage, Main.bitAmount);

        // ASCII til/fra
        if (Main.ascii == 1) {
            bufferedImage = Symbols.toAscii(bufferedImage);
            imgwidth  = bufferedImage.getWidth();
            imgheight = bufferedImage.getHeight();
        }

        //Hvis vi ønsker at gemme billedet
        if (Main.saveIMG) {
            File outputFile = new File("C:\\Github\\ASCII-art-generator\\Java\\savedIMG.png");
            ImageIO.write(bufferedImage, "png", outputFile);
            System.out.println("Billede skrevet");
            // https://www.geeksforgeeks.org/java/image-processing-in-java-read-and-write/
        }

        return bufferedImage;


    }
}
// udvid moscow
// tilføj omkirng test af modkow funktioner
