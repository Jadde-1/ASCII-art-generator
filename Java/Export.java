import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Export {

    // Åbner fildialog og indlæser det valgte billede ind i win-instansen
    public void importImage(win window) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            try {
                window.originalImage = ImageIO.read(fileChooser.getSelectedFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            window.updatePreviewLive();
        }
    }

    // Åbner gem-dialog og eksporterer billedet som PNG
    public static void exportPNG(JFrame parent, BufferedImage image) {
        if (image == null) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("ascii_art.png"));

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