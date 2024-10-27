package Handlers;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

import Processors.ActionProcessor;

import java.util.Random;

/**
 * scorehandler manages everything you see on the right hand side of the game, such as score, depth etc.
 */
public class ScoreHandler {


    private static int oreLocation = -1;
    private static boolean oreFound = false;

    private static final int DEPTH_UPDATE_INTERVAL = 10;
    

    public static int score;
    public static int depth;
    private static int TimeInSeconds;
    private static int percentage = 100;
    private static int minedOre;

    private static JLabel scoreLabel;
    private static JLabel depthLabel;
    private static JLabel timeLabel;
    private static JLabel oreLabel;
    private static JLabel oxygenLabel;

    private static Random random = new Random();

    private Font customFont;
    
    private static Timer oxygenTimer;
    private static Timer depthTimer;

    /**
     * scorehandler constructor.
     * initializes panels and manages layout
     * @param parentPanel
     */
    public ScoreHandler(JPanel parentPanel) {

        score = 0;
        depth = 0;
        TimeInSeconds = 0;
        minedOre = 0;

        // Use BoxLayout to stack components vertically
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        usingCustomFonts();

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing

        // Score
        JLabel scoreText = new JLabel("Score");
        scoreText.setFont(customFont.deriveFont(40f));
        scoreText.setForeground(Color.BLACK);
        centerPanel.add(scoreText);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(customFont.deriveFont(25f));
        scoreLabel.setForeground(Color.BLACK);
        centerPanel.add(scoreLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing

        // Time
        JLabel time = new JLabel("Time");
        time.setFont(customFont.deriveFont(40f));
        time.setForeground(Color.BLACK);
        centerPanel.add(time);

        timeLabel = new JLabel("00:00");
        timeLabel.setFont(customFont.deriveFont(25f));
        timeLabel.setForeground(Color.BLACK);
        centerPanel.add(timeLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing

        // Depth
        JLabel depthText = new JLabel("Depth");
        depthText.setFont(customFont.deriveFont(40f));
        depthText.setForeground(Color.BLACK);
        centerPanel.add(depthText);

        depthLabel = new JLabel("0");
        depthLabel.setFont(customFont.deriveFont(25f));
        depthLabel.setForeground(Color.BLACK);
        centerPanel.add(depthLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing

        // Oxygen
        JLabel oxygen = new JLabel("Oxygen");
        oxygen.setFont(customFont.deriveFont(40));
        oxygen.setForeground(Color.BLACK);
        centerPanel.add(oxygen);

        oxygenLabel = new JLabel("(100)%");
        oxygenLabel.setFont(customFont.deriveFont(25f));
        oxygenLabel.setForeground(Color.BLACK);
        centerPanel.add(oxygenLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Optional spacing

        // Ore
        JLabel ore = new JLabel("Ore");
        ore.setFont(customFont.deriveFont(40));
        ore.setForeground(Color.BLACK);
        centerPanel.add(ore);

        oreLabel = new JLabel("0 Mined");
        oreLabel.setFont(customFont.deriveFont(25f));
        oreLabel.setForeground(Color.BLACK);
        centerPanel.add(oreLabel);
        centerPanel.setOpaque(false);


        // Set alignment for the centerPanel to center in the parent panel
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the panel itself
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for the parent
        parentPanel.add(centerPanel); // Add the centered panel to the parent

    }

    /**
     * method to import and use custom fonts.
     */
    void usingCustomFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            File fontFile = new File("Assets/impacted.ttf");
            if (fontFile.exists()) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(30f);
                ge.registerFont(customFont);
                System.out.println("Font registered: " + customFont.getFontName());
            }
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading font: " + e.getMessage());
        }
    }

    /**
     * starts the depthtimer which makes the depth increase.
     */
    public static void startDepthCounting() {
        depthTimer = new Timer(DEPTH_UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depth += 1;
                updateDepthLabel();
            }
        });
        depthTimer.start();
    }

    public static void stopDepthCounting() {
        depthTimer.stop();
    }

    /*
     * starts the time counter which makes the time increase;
     */
    public static void startTimeCounting() {
        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeInSeconds++;
                updateTimeLabel();
            }
        });
        timeTimer.start();
        
    }

    /*
     * Starts the oxygen counter which makes the oxygen decrease.
     */
    public static void startOxygenCounting() {
        oxygenTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percentage = percentage - 1;
                updateOxygenLabel();
            }
        });
        oxygenTimer.start();
    }

    /*
     * stops the oxygen timer and sets the oxygen values to 100.
     */
    public static void stopOxygenCounting() {
        oxygenTimer.stop();
        percentage = 100;
        updateOxygenLabel();
    }
    
    /*
     * increases the score count by the amount of points.
     */
    public static void scoreCount(int points) {
        score += points;
        updateScoreLabel(); 
    }

    private static void updateScoreLabel() {
        scoreLabel.setText("" + score);
    }

    public static void depthCount(int depthMeters) {
        depth += depthMeters;
        updateDepthLabel();
    }

    private static void updateDepthLabel() {
        depthLabel.setText("" + depth + " km");
    }

    /**
     * updates the time label and formats it correctly.
     */
    private static void updateTimeLabel() {
        int minutes = TimeInSeconds / 60;
        int seconds = TimeInSeconds % 60;
        String timeDisplay = String.format("%02d:%02d", minutes, seconds);
        timeLabel.setText(timeDisplay);
    }

    /**
     * updates the oxygen label and formats it correctly.
     */
    private static void updateOxygenLabel() {
        int roundedPercentage = (int) Math.round(percentage);
        String oxygenDisplay = String.format("(%d%%)", roundedPercentage);
        oxygenLabel.setText(oxygenDisplay);
        if (roundedPercentage <= 0) {
            oxygenLabel.setText("(0%)"); 
            ActionProcessor.lose();
        }
    }

    public static void oreCount(int amountOfOre) {
        minedOre += amountOfOre;
        updateOreLabel();
    } 

    private static void updateOreLabel() {
        oreLabel.setText(minedOre + " Mined");
    }

    /**
     * generate a randomDepth number.
     */
    private static void generateOre() {
        int randomDepth = (random.nextInt(10) + 8) * 100;
        oreLocation = randomDepth + depth;
        oreFound = true;
    }

    public static int searchForOre() {
        generateOre();
        return oreLocation;
    }

    /**
     * manages if the ore can be mined and the appropriate behaviour.
     * @return
     */
    public static Boolean mineOre() {
        if (oreFound && (oreLocation - depth <= 500 && depth <= oreLocation)) {
            oreCount(1);
            oreFound = false;
            return true;
        } else {
            return false;
        }
    }


    

}
