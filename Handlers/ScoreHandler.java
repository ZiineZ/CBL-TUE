package Handlers;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.util.Random;

public class ScoreHandler {


    private static int oreLocation = -1;
    private static boolean oreFound = false;

    private static final int DEPTH_UPDATE_INTERVAL = 1000;
    

    private static int score;
    private static int depth;
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
    

    public ScoreHandler(JPanel parentPanel) {

        score = 0;
        depth = 0;
        TimeInSeconds = 0;
        minedOre = 0;

        parentPanel.setLayout(null);
        JLabel scoreText, time, depthText, oxygen, ore;

        usingCustomFonts();

        // Score
        scoreText = new JLabel("Score");
        scoreText.setFont(customFont.deriveFont(45f));
        scoreText.setForeground(Color.YELLOW);
        scoreText.setVisible(true);
        scoreText.setBounds(118, 30, 100, 30);
        parentPanel.add(scoreText);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(customFont);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setVisible(true);
        scoreLabel.setBounds(152, 64, 100, 30);
        parentPanel.add(scoreLabel);

        // Time
        time = new JLabel("Time");
        time.setVisible(true);
        time.setFont(customFont.deriveFont(45f));
        time.setForeground(Color.YELLOW);
        time.setBounds(130, 110, 100, 30);
        parentPanel.add(time);

        timeLabel = new JLabel("00:00");
        timeLabel.setVisible(true);
        timeLabel.setFont(customFont);
        timeLabel.setForeground(Color.YELLOW);
        timeLabel.setBounds(126, 150, 100, 30);
        parentPanel.add(timeLabel);

        // Depth
        depthText = new JLabel("Depth");
        depthText.setFont(customFont.deriveFont(45f));
        depthText.setForeground(Color.YELLOW);
        depthText.setVisible(true);
        depthText.setBounds(120, 200, 100, 30);
        parentPanel.add(depthText);

        depthLabel = new JLabel("0");
        depthLabel.setFont(customFont.deriveFont(45f));
        depthLabel.setForeground(Color.YELLOW);
        depthLabel.setVisible(true);
        depthLabel.setBounds(136, 238, 100, 30);
        parentPanel.add(depthLabel);

        // Oxygen
        oxygen = new JLabel("Oxygen");
        oxygen.setFont(customFont.deriveFont(40f));
        oxygen.setForeground(Color.YELLOW);
        oxygen.setVisible(true);
        oxygen.setBounds(114, 300, 100, 30);
        parentPanel.add(oxygen);

        oxygenLabel = new JLabel("(100)%");
        oxygenLabel.setFont(customFont);
        oxygenLabel.setForeground(Color.YELLOW);
        oxygenLabel.setVisible(true);
        oxygenLabel.setBounds(127, 340, 100, 30);
        parentPanel.add(oxygenLabel);

        // Ore
        ore  = new JLabel("Ore");
        ore.setFont(customFont.deriveFont(45f));
        ore.setForeground(Color.YELLOW);
        ore.setVisible(true);
        ore.setBounds(136, 400, 100, 30);
        parentPanel.add(ore);

        oreLabel = new JLabel("0 Mined");
        oreLabel.setFont(customFont.deriveFont(35f));
        oreLabel.setForeground(Color.YELLOW);
        oreLabel.setVisible(true);
        oreLabel.setBounds(118, 445, 100, 30);
        parentPanel.add(oreLabel);


        //startDepthCounting();
        //startTimeCounting();
        //startOxygenCounting();
    }

    void usingCustomFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            File fontFile = new File("Assets/BroncoPersonalUse.ttf");
            if (fontFile.exists()) {
                customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(40f);
                ge.registerFont(customFont);
                System.out.println("Font registered: " + customFont.getFontName());
            }
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading font: " + e.getMessage());
        }
    }

    public static void startDepthCounting() {
        Timer depthTimer = new Timer(DEPTH_UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depth += 1;
                updateDepthLabel();
            }
        });
        depthTimer.start();
    }

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

    private void startOxygenCounting() {
        Timer oxygenTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percentage = percentage - 1;
                updateOxygenLabel();
            }
        });
        oxygenTimer.start();
    }
    
    // Score
    public static void scoreCount(int points) {
        score += points;
        updateScoreLabel(); 
    }

    private static void updateScoreLabel() {
        scoreLabel.setText("" + score);
    }

    //  Depth
    public static void depthCount(int depthMeters) {
        depth += depthMeters;
        updateDepthLabel();
    }

    private static void updateDepthLabel() {
        depthLabel.setText("" + depth);
       
    }

    // Time
    private static void updateTimeLabel() {
        int minutes = TimeInSeconds / 60;
        int seconds = TimeInSeconds % 60;
        String timeDisplay = String.format("%02d:%02d", minutes, seconds);
        timeLabel.setText(timeDisplay);
    }

    // Oxygen
    private static void updateOxygenLabel() {
        int roundedPercentage = (int) Math.round(percentage);
        String oxygenDisplay = String.format("(%d%%)", roundedPercentage);
        oxygenLabel.setText(oxygenDisplay);
        if (roundedPercentage <= 0) {
            oxygenLabel.setText("(0%)"); 
        }
    }

    // Ore
    public static void oreCount(int amountOfOre) {
        minedOre += amountOfOre;
        updateOreLabel();
    } 

    private static void updateOreLabel() {
        oreLabel.setText(minedOre + " Mined");
    }

    private static void generateOre() {
        int randomDepth = (random.nextInt(10) + 8) * 100;
        oreLocation = randomDepth + depth;
        oreFound = true;
    }

    public static void searchForOre() {
        generateOre();
        System.out.println("Ore located in depth: " + oreLocation);
    }

    public static void mineOre() {
        if (oreFound && (oreLocation - depth <= 500 && depth <= oreLocation)) {
            oreCount(1);
            scoreCount(500);
            oreFound = false;
        } else {
            System.out.println("Nothing FOUND!");
        }
    }


    

}
