package Processors;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * this class handles the loading and playing of custom sounds.
 */
public class SoundProcessor {

    private Clip problemSound;
    private Clip solvedSound;
    private Clip backgroundMusic;

    public SoundProcessor() {
        problemSound = loadSound("Assets/problem.wav");
        solvedSound = loadSound("Assets/resolved.wav");
        backgroundMusic = loadSound("Assets/backgroundmusic.wav");
    }

    /**
     * properly loads the sounds to the file.
     */
    private Clip loadSound(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * the upcoming methods all make sure that their own sound is being played.
     */
    public void playProblemSound() {
        if (problemSound != null) {
            problemSound.setFramePosition(0);
            problemSound.start();
        }
    }

    public void playSolvedSound() {
        if (solvedSound != null) {
            solvedSound.setFramePosition(0);
            solvedSound.start();
        }
    }
    public void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.start();
    }
}
}