package Handlers;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class visualHandler extends JPanel {
    
    private Image image;

       private void loadImage() {
        try {
            // Using ImageIO to load the image
            image = ImageIO.read(new File("Assets/controlroomimage.jpeg"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's method
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Draw the image at (0, 0)
        }
    }

    public visualHandler() {
        loadImage();
    }

}
