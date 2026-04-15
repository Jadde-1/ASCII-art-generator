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

    // Top buttons
    static JButton importBtn, exportImgBtn;
    // Sliders
    static JSlider scaleSlider, bitAmountSlider, charQuality;
    // Checkboxes
    static JCheckBox asciiCheck, greyCheck;
    // Dropdowns
    static JComboBox<String> charTypeDropdown, fontDropdown, fontStyleDropdown;

    static JLabel imageLabel;

    public int imgwidth;
    public int imgheight;

    static JFrame f;
    static JButton b, b1, b2;
    static JLabel l;

    static File currentImageFile = null;
    BufferedImage originalImage = null;
    BufferedImage displayImage = null;

    String imgpath = "C:\\Users\\Vrill\\Documents\\GitHub\\ASCII-art-generator\\Java\\doge.png";
    // Jasper path: "C:\\Github\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // J path 2: "C:\\Users\\Vrill\\Documents\\GitHub\\ASCII-art-generator\\Java\\TEST-IMG.jpg"
    // Malik path: "C:\\Users\\malik\\IdeaProjects\\ASCII\\src\\L-1253-00-000003-wpu.jpg"
    win(){
        JFrame frame = new JFrame("ASCII Art Generator");
        frame.setPreferredSize(new Dimension(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.gray);

        // TOP PANEL - Buttons
        JPanel topPanel = createTopPanel();
        topPanel.setPreferredSize(new Dimension(width, 60));

        // LEFT PANEL - Controls
        JPanel leftPanel = createControlPanel();
        leftPanel.setPreferredSize(new Dimension(350, height));

        JPanel rightPanel = createImagePanel();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(220, 220, 220));


        // Import det billede man vil konvertere
        importBtn = new JButton("Import Image");
        importBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        importBtn.setBackground(new Color(0, 0, 0));
        importBtn.setForeground(Color.WHITE);
        importBtn.setFocusPainted(false);
        importBtn.setMargin(new Insets(8, 15, 8, 15));
        importBtn.addActionListener(e -> importImage());
        panel.add(importBtn);

        // Export Image
        exportImgBtn = new JButton("Export Image");
        exportImgBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        exportImgBtn.setBackground(new Color(0, 0, 0));
        exportImgBtn.setForeground(Color.WHITE);
        exportImgBtn.setFocusPainted(false);
        exportImgBtn.setMargin(new Insets(8, 15, 8, 15));
        //exportImgBtn.addActionListener(e -> exportImage());
        panel.add(exportImgBtn);


        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(128, 128, 128));

        return panel;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setLayout(new BorderLayout());


        imageLabel = new JLabel("Drag and drop an image here or use Import button", SwingConstants.CENTER);
        imageLabel.setBackground(new Color(192, 192, 192));
        imageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        imageLabel.setForeground(new Color(120, 120, 120));
        panel.add(imageLabel, BorderLayout.CENTER);

        //setupDragAndDrop(panel);

        return panel;
    }

    private void importImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "png", "jpg", "jpeg", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            loadImage(fileChooser.getSelectedFile());
        }
    }

    private void loadImage(File file) {
            currentImageFile = file;
        try {
            originalImage = ImageIO.read(file);
            // Skal også opdatere ellers kaster den fejl hvis der ikke er noget bilelde at loade
            //updatePreview();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
