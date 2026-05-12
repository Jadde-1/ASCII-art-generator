import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class win extends JFrame {
    int height = 850;
    int width = 1600;

    // Tekstfarve på ASCII-tegn (RGB)
    public static int Rrgb = 0;
    public static int Grgb = 0;
    public static int Brgb = 0;

    // Top buttons
    JButton importBtn, exportImgBtn;
    // Sliders
    JSlider scaleSlider, bitAmountSlider, charScaleSlider;
    // Checkboxes
    JCheckBox asciiCheck, greyCheck;
    // Dropdowns
    JComboBox<String> charTypeDropdown, fontDropdown, fontStyleDropdown;
    // RGB tekstfelter
    JTextField RrgbField, BrgbField, GrgbField;

    JLabel imageLabel;

    BufferedImage originalImage = null;
    BufferedImage displayImage = null;

    win() {
        this.setTitle("ASCII Art Generator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bgColor = new Color(240, 240, 240);
        Color panelColor = new Color(250, 250, 250);

        UIManager.put("Panel.background", bgColor);
        UIManager.put("Label.foreground", new Color(50, 50, 50));

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(bgColor);

        JPanel topPanel = createTopPanel();
        topPanel.setPreferredSize(new Dimension(width, 60));

        JPanel leftPanel = createControlPanel();
        leftPanel.setPreferredSize(new Dimension(350, height));

        JPanel rightPanel = createImagePanel(panelColor);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(220, 220, 220));

        importBtn = new JButton("Import Image");
        styleButton(importBtn);
        importBtn.addActionListener(e -> new Export().importImage(this));
        panel.add(importBtn);

        exportImgBtn = new JButton("Export Image");
        styleButton(exportImgBtn);
        exportImgBtn.addActionListener(e -> Export.exportPNG(this, displayImage));
        panel.add(exportImgBtn);

        return panel;
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setBackground(new Color(0, 0, 0));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(8, 15, 8, 15));
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(128, 128, 128));

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 0, 0));
        panel.add(titleLabel);

        scaleSlider = new JSlider(0, 50, (int) Main.scale);
        panel.add(createLabeledSlider("Scale:", scaleSlider));
        scaleSlider.setBackground(new Color(128, 128, 128));

        bitAmountSlider = new JSlider(2, 24, Main.bitAmount);
        panel.add(createLabeledSlider("Colors (Bit Amount):", bitAmountSlider));
        bitAmountSlider.setBackground(new Color(128, 128, 128));

        greyCheck = new JCheckBox("Grayscale", Main.grey);
        greyCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        greyCheck.setBackground(new Color(128, 128, 128));
        greyCheck.setMaximumSize(new Dimension(330, 25));
        greyCheck.addActionListener(e -> updatePreviewLive());
        panel.add(greyCheck);

        asciiCheck = new JCheckBox("Enable ASCII Mode", Main.ascii);
        asciiCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        asciiCheck.setBackground(new Color(128, 128, 128));
        asciiCheck.setMaximumSize(new Dimension(330, 25));
        asciiCheck.addActionListener(e -> updatePreviewLive());
        panel.add(asciiCheck);

        charScaleSlider = new JSlider(1, 10, Main.charScale);
        panel.add(createLabeledSlider("Character Quality:", charScaleSlider));
        charScaleSlider.setBackground(new Color(128, 128, 128));

        String[] charTypes = {"Letters", "Numbers", "Mixed", "Full", "Blocks", "Rain"};
        charTypeDropdown = createDropdown("Character Type:", charTypes, Main.text - 1);
        charTypeDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(charTypeDropdown.getParent());

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontDropdown = createDropdown("Font:", fonts, 0);
        fontDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(fontDropdown.getParent());

        String[] styles = {"Normal", "Bold", "Italic", "Bold Italic"};
        fontStyleDropdown = createDropdown("Style:", styles, 0);
        fontStyleDropdown.addActionListener(e -> updatePreviewLive());
        panel.add(fontStyleDropdown.getParent());

        // RGB felter — tilføj wrapper-panelet direkte til controlPanel
        panel.add(createRgbPanel("R (0-255):", Color.RED, "R"));
        panel.add(createRgbPanel("G (0-255):", Color.green, "G"));
        panel.add(createRgbPanel("B (0-255):", Color.blue, "B"));

        return panel;
    }

    private JPanel createLabeledSlider(String labelText, JSlider slider) {
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

        slider.addChangeListener(e -> {
            if (!slider.getValueIsAdjusting()) {
                updatePreviewLive();
            }
        });
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
        for (Object item : items) dropdown.addItem(item.toString());
        dropdown.setSelectedIndex(Math.min(selectedIndex, items.length - 1));
        dropdown.setMaximumSize(new Dimension(200, 25));

        panel.add(label, BorderLayout.WEST);
        panel.add(dropdown, BorderLayout.CENTER);
        panel.putClientProperty("dropdown", dropdown);
        return dropdown;
    }

    // Returnerer hele wrapper-panelet (label + felt) så det kan tilføjes korrekt
    private JPanel createRgbPanel(String labelText, Color labelColor, String channel) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(new Color(128, 128, 128));
        wrapper.setMaximumSize(new Dimension(330, 50));

        JLabel label = new JLabel(labelText);
        label.setForeground(labelColor);

        JTextField field = new JTextField("0");
        field.setMaximumSize(new Dimension(330, 25));

        // Gem reference til feltet
        switch (channel) {
            case "R" -> RrgbField = field;
            case "G" -> GrgbField = field;
            case "B" -> BrgbField = field;
        }

        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { tryUpdate(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { tryUpdate(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { tryUpdate(); }

            void tryUpdate() {
                    int val = Math.max(0, Math.min(255, Integer.parseInt(field.getText().trim())));
                    switch (channel) {
                        case "R" -> Rrgb = val;
                        case "G" -> Grgb = val;
                        case "B" -> Brgb = val;
                    }
                    updatePreviewLive();

            }
        });

        wrapper.add(label);
        wrapper.add(field);
        return wrapper;
    }

    private JPanel createImagePanel(Color panelColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelColor);

        imageLabel = new JLabel("Import et billede med knappen i venstre hjørne", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        imageLabel.setForeground(new Color(120, 120, 120));
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    public void updatePreviewLive() {
        if (originalImage == null) return;
            displayImage = processImage(originalImage);


        if (displayImage == null) return;
        int maxWidth = 1000;
        int maxHeight = 700;
        double scale = Math.min((double) maxWidth / displayImage.getWidth(), (double) maxHeight / displayImage.getHeight());
        int pw = (int) (displayImage.getWidth() * scale);
        int ph = (int) (displayImage.getHeight() * scale);
        imageLabel.setIcon(new ImageIcon(displayImage.getScaledInstance(pw, ph, Image.SCALE_SMOOTH)));
        imageLabel.setText(null);
    }

    private BufferedImage processImage(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(img, 0, 0, null);

        Main.scale = scaleSlider.getValue();
        Main.bitAmount = bitAmountSlider.getValue();
        Main.charScale = charScaleSlider.getValue();
        Main.text = charTypeDropdown.getSelectedIndex() + 1;
        Main.ascii = asciiCheck.isSelected();
        Main.grey = greyCheck.isSelected();

        result = Downscaler.down(result, Main.scale);

        if (Main.grey) {
            result = GrayScaler.Gray(result);
        }
        result = Quantization.color(result, Main.bitAmount);

        if (Main.ascii) {
            String fontName = fontDropdown.getSelectedItem().toString();
            int fontStyle = switch (fontStyleDropdown.getSelectedIndex()) {
                case 1 -> Font.BOLD;
                case 2 -> Font.ITALIC;
                case 3 -> Font.BOLD | Font.ITALIC;
                default -> Font.PLAIN;
            };
            Symbols.setFont(fontName, fontStyle);
            result = Symbols.toAscii(result);
        }
        return result;
    }
}