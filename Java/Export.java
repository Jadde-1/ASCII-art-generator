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
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Image Files", "png", "jpg", "jpeg", "gif", "bmp", "webp"));

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(file);
                if (img == null) {
                    JOptionPane.showMessageDialog(parent, "Ugyldig billedfil.", "Fejl", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                return img;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Kunne ikke indlæse filen:\n" + e.getMessage(), "Fejl", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    // Åbner gem-dialog og eksporterer billedet som PNG
    public static void exportPNG(JFrame parent, BufferedImage image) {
        if (image == null) {
            JOptionPane.showMessageDialog(parent, "Intet billede at eksportere.", "Fejl", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(parent, "Billede gemt: " + file.getName(), "Gemt!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Kunne ikke gemme filen:\n" + e.getMessage(), "Fejl", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}