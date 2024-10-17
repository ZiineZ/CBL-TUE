package Handlers;

import java.awt.Font;

import javax.swing.*;

/**
 * Com.
 */
public class ScoreHandler {
    
    private int score;
    private JLabel scoreLabel;

    public ScoreHandler(JPanel parentPanel) {

        score = 0;

        parentPanel.setLayout(null);

        JLabel scoreText, time, depth, oxygen, ore;

        scoreText = new JLabel("Score");
        scoreText.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        scoreText.setVisible(true);
        scoreText.setBounds(132, 30, 100, 30);
        parentPanel.add(scoreText);

        scoreLabel = new JLabel("");
        scoreLabel.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        scoreLabel.setVisible(true);
        scoreLabel.setBounds(140, 50, 100, 30);
        parentPanel.add(scoreLabel);

        time = new JLabel("Time");
        time.setVisible(true);
        time.setBounds(100, 100, 100, 30);
        parentPanel.add(time);

        depth = new JLabel("Depth");
        depth.setVisible(true);
        depth.setBounds(100, 150, 100, 30);
        parentPanel.add(depth);

        oxygen = new JLabel("Oxygen");
        oxygen.setVisible(true);
        oxygen.setBounds(100, 200, 100, 30);
        parentPanel.add(oxygen);

        ore  = new JLabel("Ore");
        ore.setVisible(true);
        ore.setBounds(100, 250, 100, 30);
        parentPanel.add(ore);

        
    }

    public void scoreCount(int points) {
        score += points;
        updateScoreLabel(); 
    }

    private void updateScoreLabel() {
        scoreLabel.setText("" + score);
    }

}
