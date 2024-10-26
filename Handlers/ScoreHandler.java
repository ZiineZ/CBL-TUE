package Handlers;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class ScoreHandler {


    private static int oreLocation = -1;
    private static boolean oreFound = false;

    private static final int depthIncrementRate = 1;
    private static final int depthUpdateInterval = 1000;
    

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

    private static boolean oreAtDepth = false;
    private static int currentOreDepth = -1;

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
        scoreText.setFont(customFont);
        scoreText.setVisible(true);
        scoreText.setBounds(120, 30, 100, 30);
        parentPanel.add(scoreText);

        scoreLabel = new JLabel("0");
        scoreLabel.setFont(customFont);
        scoreLabel.setVisible(true);
        scoreLabel.setBounds(140, 60, 100, 30);
        parentPanel.add(scoreLabel);

        // Time
        time = new JLabel("Time");
        time.setVisible(true);
        time.setFont(customFont);
        time.setBounds(130, 120, 100, 30);
        parentPanel.add(time);

        timeLabel = new JLabel("00:00");
        timeLabel.setVisible(true);
        timeLabel.setFont(customFont);
        timeLabel.setBounds(120, 150, 100, 30);
        parentPanel.add(timeLabel);

        // Depth
        depthText = new JLabel("Depth");
        depthText.setFont(customFont);
        depthText.setVisible(true);
        depthText.setBounds(122, 200, 100, 30);
        parentPanel.add(depthText);

        depthLabel = new JLabel("0");
        depthLabel.setFont(customFont);
        depthLabel.setVisible(true);
        depthLabel.setBounds(120, 230, 100, 30);
        parentPanel.add(depthLabel);

        // Oxygen
        oxygen = new JLabel("Oxygen");
        oxygen.setFont(customFont);
        oxygen.setVisible(true);
        oxygen.setBounds(112, 300, 100, 30);
        parentPanel.add(oxygen);

        oxygenLabel = new JLabel("(100)%");
        oxygenLabel.setFont(customFont);
        oxygenLabel.setVisible(true);
        oxygenLabel.setBounds(122, 340, 100, 30);
        parentPanel.add(oxygenLabel);

        // Ore
        ore  = new JLabel("Ore");
        ore.setFont(customFont);
        ore.setVisible(true);
        ore.setBounds(134, 400, 100, 30);
        parentPanel.add(ore);

        oreLabel = new JLabel("0 Mined");
        oreLabel.setFont(customFont.deriveFont(25f));
        oreLabel.setVisible(true);
        oreLabel.setBounds(124, 430, 100, 30);
        parentPanel.add(oreLabel);


        startDepthCounting();
        startTimeCounting();
        startOxygenCounting();
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

    private void startDepthCounting() {
        Timer depthTimer = new Timer(depthUpdateInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depth += 100;
                updateDepthLabel();
            }
        });
        depthTimer.start();
    }

    private void startTimeCounting() {
        Timer timeTimer = new Timer(1000, new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeInSeconds++;
                updateTimeLabel();
            }
        });
        timeTimer.start();
        
    }

    private void startOxygenCounting() {
        Timer oxygenTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percentage= percentage - 10;
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
        String oxygenDisplay = String.format("(%02d%%)", percentage);
        oxygenLabel.setText(oxygenDisplay);
        if (percentage == 0) {
            oxygenDisplay = String.format("(0%)");
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
        if (oreFound == true && (oreLocation - depth <= 500 && depth <= oreLocation)) {
            oreCount(1);
            scoreCount(500);
            oreFound = false;
        } else {
            System.out.println("Nothing FOUND!");
        }
    }

}
