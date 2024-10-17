package Handlers;

import java.awt.*;
import java.io.*;
import javax.swing.*;


public class visualHandler extends JPanel {

    JPanel temperaturePanel = new JPanel();
    JPanel ventilationPanel = new JPanel();
    JPanel fuelPanel = new JPanel();
    JPanel orePanel = new JPanel();

    public visualHandler() {

        this.setLayout(new GridLayout(2, 2));
        this.setVisible(true);

        temperaturePanel.setBackground(Color.GRAY);
        ventilationPanel.setBackground(Color.DARK_GRAY);
        fuelPanel.setBackground(Color.DARK_GRAY);
        orePanel.setBackground(Color.GRAY);

        temperaturePanel.setVisible(true);
        ventilationPanel.setVisible(true);
        fuelPanel.setVisible(true);
        orePanel.setVisible(true);


        this.add(temperaturePanel);
        this.add(ventilationPanel);
        this.add(fuelPanel);
        this.add(orePanel);

        addImageIcons();

    }

    void addImageIcons() {

    }

}
