import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class win extends JFrame {
    int height = 850;
    int width = 1600;

    public static int Rrgb = 0;
    public int Grgb = 0;
    public int Brgb = 0;

    // Top buttons
    static JButton importBtn, exportImgBtn, exportTxtBtn, copyClipboardBtn;
    // Sliders
    static JSlider scaleSlider, bitAmountSlider, charScaleSlider;
    // Checkboxes
    static JCheckBox asciiCheck, greyCheck;
    // Dropdowns
    static JComboBox<String> charTypeDropdown, fontDropdown, fontStyleDropdown;

    public static JTextField RrgbField, BrgbField, GrgbField;
    static JLabel imageLabel;

    static File currentImageFile = null;
    BufferedImage originalImage = null;
    BufferedImage displayImage = null;

    win() {
        this.setTitle("ASCII Art Generator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(width, height));
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        // Color scheme
        Color bgColor = new Color(240, 240, 240);
        Color panelColor = new Color(250, 250, 250);
        Color accentColor = new Color(60, 120, 180);

        UIManager.put("Panel.background", bgColor);
        UIManager.put("Label.foreground", new Color(50, 50, 50));

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(bgColor);

        // TOP PANEL - Buttons
        JPanel topPanel = createTopPanel(accentColor);
        topPanel.setPreferredSize(new Dimension(width, 60));

        // LEFT PANEL - Controls
        JPanel leftPanel = createControlPanel(accentColor, panelColor);
        leftPanel.setPreferredSize(new Dimension(350, height));

        // RIGHT PANEL - Image Preview
        JPanel rightPanel = createImagePanel(panelColor);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private JPanel createTopPanel(Color accentColor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(220, 220, 220));

        // Import
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
        exportImgBtn.addActionListener(e -> exportImage());
        panel.add(exportImgBtn);


        return panel;
    }

    private JPanel createControlPanel(Color accentColor, Color panelColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(128, 128, 128));

        // Title
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 0, 0));
        panel.add(titleLabel);

        // Scale
        panel.add(createLabeledSlider("Scale:", scaleSlider = new JSlider(0, 50, (int) Main.scale),
                e -> updatePreviewLive()));
        scaleSlider.setBackground(new Color(128, 128, 128));

        // Bit Amount
        panel.add(createLabeledSlider("Colors (Bit Amount):", bitAmountSlider = new JSlider(2, 256, Main.bitAmount),
                e -> updatePreviewLive()));
        bitAmountSlider.setBackground(new Color(128, 128, 128));

        // Grayscale Checkbox
        greyCheck = new JCheckBox("Grayscale", Main.grey == 1);
        greyCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        greyCheck.setBackground(new Color(128, 128, 128));
        greyCheck.setMaximumSize(new Dimension(330, 25));
        greyCheck.addActionListener(e -> updatePreviewLive());
        panel.add(greyCheck);

        // ASCII Mode
        asciiCheck = new JCheckBox("Enable ASCII Mode", Main.ascii == 1);
        asciiCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        asciiCheck.setBackground(new Color(128, 128, 128));
        asciiCheck.setMaximumSize(new Dimension(330, 25));
        asciiCheck.addActionListener(e -> updatePreviewLive());
        panel.add(asciiCheck);

        // Character Scale
        panel.add(createLabeledSlider("Character Quality:", charScaleSlider = new JSlider(1, 10, Main.charScale),
                e -> updatePreviewLive()));
        charScaleSlider.setBackground(new Color(128, 128, 128));

        // Character Type
        String[] charTypes = {"Letters", "Numbers", "Mixed", "Full", "Blocks", "Rain"};
        charTypeDropdown = createDropdown("Character Type:", charTypes, Main.text - 1);
        charTypeDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(charTypeDropdown.getParent());

        // Font Selection
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontDropdown = createDropdown("Font:", fonts, 0);
        fontDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(fontDropdown.getParent());

        // Font Style
        String[] styles = {"Normal", "Bold", "Italic", "Bold Italic", "Thin"};
        fontStyleDropdown = createDropdown("Style:", styles, 0);
        fontStyleDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(fontStyleDropdown.getParent());

        // RGB variabler der kan ændres

        RrgbField = new JTextField(getRrgb());
        RrgbField.setMaximumSize(new Dimension(60, 25));
        panel.add(RrgbField);
        RrgbField.setColumns(3);


        GrgbField = new JTextField(Grgb);
        GrgbField.setMaximumSize(new Dimension(60, 25));
        panel.add(GrgbField);
        GrgbField.setColumns(3);


        BrgbField = new JTextField(Brgb);
        BrgbField.setMaximumSize(new Dimension(60, 25));
        panel.add(BrgbField);
        BrgbField.setColumns(3);

        return panel;
    }

    private JPanel createLabeledSlider(String labelText, JSlider slider, javax.swing.event.ChangeListener listener) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(128, 128, 128));
        panel.setMaximumSize(new Dimension(330, 60));
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 11));

        slider.setMaximumSize(new Dimension(330, 50));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(slider.getMaximum() / 5);
        slider.setBackground(new Color(250, 250, 250));
        slider.addChangeListener(listener);

        panel.add(label, BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }

    private JComboBox<String> createDropdown(String labelText, Object[] items, int selectedIndex) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(128, 128, 128));
        panel.setMaximumSize(new Dimension(330, 35));
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setPreferredSize(new Dimension(100, 25));

        JComboBox<String> dropdown = new JComboBox<>();
        for (Object item : items) {
            dropdown.addItem(item.toString());
        }
        dropdown.setSelectedIndex(Math.min(selectedIndex, items.length - 1));
        dropdown.setMaximumSize(new Dimension(200, 25));

        panel.add(label, BorderLayout.WEST);
        panel.add(dropdown, BorderLayout.CENTER);

        // Store reference for later access
        panel.putClientProperty("dropdown", dropdown);
        return dropdown;
    }

    private JPanel createImagePanel(Color panelColor) {
        JPanel panel = new JPanel();
        panel.setBackground(panelColor);
        panel.setLayout(new BorderLayout());

        imageLabel = new JLabel("Drag and drop an image here or use Import button", SwingConstants.CENTER);
        imageLabel.setBackground(new Color(192, 192, 192));
        imageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        imageLabel.setForeground(new Color(120, 120, 120));
        panel.add(imageLabel, BorderLayout.CENTER);

        setupDragAndDrop(panel);

        return panel;
    }

    private void setupDragAndDrop(JPanel panel) {
        DropTarget dropTarget = new DropTarget(panel, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    List<File> droppedFiles = (List<File>) dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        if (isImageFile(file)) {
                            loadImage(file);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        try {
            currentImageFile = file;
            originalImage = ImageIO.read(file);
            updatePreviewLive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void updatePreviewLive() {
        if (originalImage == null) return;

        SwingWorker<BufferedImage, Void> worker = new SwingWorker<BufferedImage, Void>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                return processImage(originalImage);
            }

            @Override
            protected void done() {
                try {
                    displayImage = get();
                    showPreview(displayImage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void showPreview(BufferedImage img) {
        if (img != null) {
            int maxWidth = 1000;
            int maxHeight = 700;

            double scale = Math.min((double) maxWidth / img.getWidth(),
                    (double) maxHeight / img.getHeight());

            int previewWidth = (int) (img.getWidth() * scale);
            int previewHeight = (int) (img.getHeight() * scale);

            Image scaledImage = img.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
            imageLabel.setText(null);
        }
    }

    private void exportImage() {
        if (displayImage == null) {
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("ascii_art.png"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Files", "png"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            try {
                ImageIO.write(displayImage, "png", fileChooser.getSelectedFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }




    private BufferedImage processImage(BufferedImage img) throws IOException {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(img, 0, 0, null);

        Main.scale = scaleSlider.getValue();
        Main.bitAmount = bitAmountSlider.getValue();
        Main.charScale = charScaleSlider.getValue();
        Main.text = charTypeDropdown.getSelectedIndex() + 1;
        Main.ascii = asciiCheck.isSelected() ? 1 : 0;
        Main.grey = greyCheck.isSelected() ? 1 : 0;

        result = Downscaler.down(result, Main.scale);

        if (Main.grey == 1) {
            result = GrayScaler.Gray(result);
        }

        result = Quantization.color(result, Main.bitAmount);

        if (Main.ascii == 1) {
            // Update font before converting to ASCII
            String fontName = fontDropdown.getSelectedItem().toString();
            int fontStyle = getFontStyle();
            Symbols.setFont(fontName, fontStyle);

            result = Symbols.toAscii(result);
        }

        return result;
    }

    private int getFontStyle() {
        int style = switch (fontStyleDropdown.getSelectedIndex()) {
            case 1 -> Font.BOLD;
            case 2 -> Font.ITALIC;
            case 3 -> Font.BOLD | Font.ITALIC;
            default -> Font.PLAIN;
        };

        // Handle thin font (using PLAIN with smaller size)
        if (fontStyleDropdown.getSelectedIndex() == 4) {
            style = Font.PLAIN;
        }

        return style;
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png") || name.endsWith(".jpg") ||
                name.endsWith(".jpeg") || name.endsWith(".gif") ||
                name.endsWith(".bmp") || name.endsWith(".webp");
    }

    public int getRrgb() {
        return Rrgb;
    }

    public void setRrgb(int rrgb) {
        Rrgb = rrgb;
    }
}