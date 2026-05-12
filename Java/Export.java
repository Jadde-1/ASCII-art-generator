import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Export {

    // Åbner fildialog og returnerer det valgte billede, eller null hvis annulleret/fejl
    public static BufferedImage importImage(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(file);
                if (img == null) {
                    return null;
                }
                return img;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    // Åbner gem-dialog og eksporterer billedet som PNG
    public static void exportPNG(JFrame parent, BufferedImage image) {
        if (image == null) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("ascii_art.png"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Files", "png"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}