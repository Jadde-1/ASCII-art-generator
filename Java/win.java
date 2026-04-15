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

    // Custom shit to change or dang variaubuls
    public double scale = 0;
    public int colors = 24; //to be used
    public  int bitAmount = 24;
    public int text = 1;
    public int charScale = 3;
    public boolean saveIMG = true;
    public boolean ASCII = true;
    public boolean GreyScale = true;


    public int imgwidth;
    public int imgheight;

    static JFrame f;
    static JButton b, b1, b2;
    static JLabel l;

    String imgpath = "C:\\Github\\ASCII-art-generator\\Java\\doge.png";
    // Jasper path: "C:\\Github\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // J path 2: "C:\\Users\\Vrill\\Documents\\GitHub\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // Malik path: "C:\\Users\\malik\\IdeaProjects\\ASCII\\src\\L-1253-00-000003-wpu.jpg"
    win(){
        f = new JFrame("ASCII Art Generator");

        l = new JLabel("test label");
        //Buttons
        b = new JButton("Toggle ASCII");
        b1 = new JButton("Toggle ASCII2");

        JPanel p = new JPanel();

        p.add(l);
        p.add(this);
        p.add(b);
        p.add(b1);

        f.setVisible(true);
        f.setPreferredSize(new Dimension(width,height));

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();

        JPanel border = new JPanel(new BorderLayout());



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
