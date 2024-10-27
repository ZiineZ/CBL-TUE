package Handlers;

import java.awt.*;
import javax.swing.*;

public class visualHandler extends JPanel {

    static Image metalbackground = new ImageIcon("Assets/metalpanel.png").getImage();

    static JPanel temperaturePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(metalbackground, 0, 0, getWidth(), getHeight(), this);
        }
    };
    static JPanel ventilationPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(metalbackground, 0, 0, getWidth(), getHeight(), this);
        }
    };
    static JPanel fuelPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(metalbackground, 0, 0, getWidth(), getHeight(), this);
        }
    };
    static JPanel orePanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(metalbackground, 0, 0, getWidth(), getHeight(), this);
        }
    };

    

    JLabel tempicon = new JLabel();
    JLabel venticon = new JLabel();
    JLabel fuelicon = new JLabel();
    JLabel oreicon = new JLabel();

    static JLabel templampicon = new JLabel();
    static JLabel ventlampicon = new JLabel();
    static JLabel fuellampicon = new JLabel();
    JLabel orelampicon = new JLabel();

    ImageIcon temp = new ImageIcon("Assets/temperaturemeter.png");
    ImageIcon vent = new ImageIcon("Assets/ventilationmeter.png");
    ImageIcon fuel = new ImageIcon("Assets/gasmeter.png");
    ImageIcon ore = new ImageIcon("Assets/oremeter.png");

    static ImageIcon templamp = new ImageIcon("Assets/lightbulboff.png");
    static ImageIcon ventlamp = new ImageIcon("Assets/lightbulboff.png");
    static ImageIcon fuellamp = new ImageIcon("Assets/lightbulboff.png");
    ImageIcon orelamp = new ImageIcon("Assets/lightbulboff.png");

    public visualHandler() {

        this.setLayout(new GridLayout(2, 2));
        this.setBorder(BorderFactory.createEmptyBorder());

        temperaturePanel.setLayout(new GridBagLayout());
        ventilationPanel.setLayout(new GridBagLayout());
        fuelPanel.setLayout(new GridBagLayout());
        orePanel.setLayout(new GridBagLayout());

        temperaturePanel.setBackground(Color.GRAY);
        ventilationPanel.setBackground(Color.DARK_GRAY);
        fuelPanel.setBackground(Color.DARK_GRAY);
        orePanel.setBackground(Color.GRAY);

        this.add(temperaturePanel);
        this.add(ventilationPanel);
        this.add(fuelPanel);
        this.add(orePanel);

        addImageIcons();
    }

    void addImageIcons() {

        resizeIcons(tempicon, temp, temperaturePanel, 80);
        resizeIcons(venticon, vent, ventilationPanel, 80);
        resizeIcons(fuelicon, fuel, fuelPanel, 80);
        resizeIcons(oreicon, ore, orePanel, 80);

        resizeIcons(templampicon, templamp, temperaturePanel, 120);
        resizeIcons(ventlampicon, ventlamp, ventilationPanel, 120);
        resizeIcons(fuellampicon, fuellamp, fuelPanel, 120);
        resizeIcons(orelampicon, orelamp, orePanel, 120);

        temperaturePanel.add(tempicon);
        ventilationPanel.add(venticon);
        fuelPanel.add(fuelicon);
        orePanel.add(oreicon);

        temperaturePanel.add(templampicon);
        ventilationPanel.add(ventlampicon);
        fuelPanel.add(fuellampicon);
        orePanel.add(orelampicon);
    }

    private static void resizeIcons(JLabel label,ImageIcon imageIcon, JPanel panel, int height) {
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(height, height, Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(scaledImage);
        label.setIcon(newImageIcon);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(label, gbc);

    }

    public static void changeLight(boolean state, int action) {
        ImageIcon icon;
        switch (action) {
            case 0:
                icon = state ? new ImageIcon("Assets/lightbulb.png") : new ImageIcon("Assets/lightbulboff.png");
                resizeIcons(templampicon, icon, temperaturePanel, 120);
                temperaturePanel.revalidate();
                temperaturePanel.repaint();
                break;
            case 1:
                icon = state ? new ImageIcon("Assets/lightbulb.png") : new ImageIcon("Assets/lightbulboff.png");
                resizeIcons(ventlampicon, icon, ventilationPanel, 120);
                ventilationPanel.revalidate();
                ventilationPanel.repaint();
                break;
            case 2:
                icon = state ? new ImageIcon("Assets/lightbulb.png") : new ImageIcon("Assets/lightbulboff.png");
                resizeIcons(fuellampicon, icon, fuelPanel, 120);
                fuelPanel.revalidate();
                fuelPanel.repaint();
                break;
        }
    }

}
