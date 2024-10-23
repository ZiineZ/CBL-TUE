package Handlers;

import java.awt.*;
import javax.swing.*;

public class visualHandler extends JPanel {

    JPanel temperaturePanel = new JPanel();
    JPanel ventilationPanel = new JPanel();
    JPanel fuelPanel = new JPanel();
    JPanel orePanel = new JPanel();

    ImageIcon temp = new ImageIcon("Assets/temperaturemeter.png");
    ImageIcon vent = new ImageIcon("Assets/ventilationmeter.png");
    ImageIcon fuel = new ImageIcon("Assets/gasmeter.png");
    ImageIcon ore = new ImageIcon("Assets/oremeter.png");

    ImageIcon templamp = new ImageIcon("Assets/lightbulboff.png");
    ImageIcon ventlamp = new ImageIcon("Assets/lightbulboff.png");
    ImageIcon fuellamp = new ImageIcon("Assets/lightbulboff.png");
    ImageIcon orelamp = new ImageIcon("Assets/lightbulboff.png");

    public visualHandler() {

        this.setLayout(new GridLayout(2, 2));

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
        
        JLabel tempicon = new JLabel();
        JLabel venticon = new JLabel();
        JLabel fuelicon = new JLabel();
        JLabel oreicon = new JLabel();

        JLabel templampicon = new JLabel();
        JLabel ventlampicon = new JLabel();
        JLabel fuellampicon = new JLabel();
        JLabel orelampicon = new JLabel();

        temperaturePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ventilationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        fuelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        orePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        temperaturePanel.add(tempicon);
        ventilationPanel.add(venticon);
        fuelPanel.add(fuelicon);
        orePanel.add(oreicon);

        temperaturePanel.add(templampicon);
        ventilationPanel.add(ventlampicon);
        fuelPanel.add(fuellampicon);
        orePanel.add(orelampicon);



        resizeAndSetIcon(tempicon, temp, temperaturePanel);
        resizeAndSetIcon(venticon, vent, ventilationPanel);
        resizeAndSetIcon(fuelicon, fuel, fuelPanel);
        resizeAndSetIcon(oreicon, ore, orePanel);

        resizeAndSetIcon(templampicon, templamp, temperaturePanel);
        resizeAndSetIcon(ventlampicon, ventlamp, ventilationPanel);
        resizeAndSetIcon(fuellampicon, fuellamp, fuelPanel);
        resizeAndSetIcon(orelampicon, orelamp, orePanel);
    }

    private void resizeAndSetIcon(JLabel label, ImageIcon icon, JPanel panel) {

        int panelHeight = panel.getHeight();
        int panelWidth = panel.getWidth();

        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);

        if (panelHeight == 0 || panelWidth == 0) {
            panel.addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    ImageIcon resizedIcon = resizedIcon(icon, panel.getHeight()  - 50);
                    label.setIcon(resizedIcon);
                    label.revalidate();
                    label.repaint();
                }
            });
        } else {
            ImageIcon resizedIcon = resizedIcon(icon, panelHeight - 50);
            label.setIcon(resizedIcon);
        }
    }

    private ImageIcon resizedIcon(ImageIcon icon, int height) {
        Image image = icon.getImage();
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);
        int newWidth = (int) ((double) height / originalHeight * originalWidth);

        Image resizedImage = image.getScaledInstance(newWidth, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
