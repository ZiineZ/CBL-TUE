package Processors;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundProcessor {

    private Clip problemSound;
    private Clip solvedSound;

    public SoundProcessor() {
        problemSound = loadSound("Assets/problem.wav");
        solvedSound = loadSound("Assets/resolved.wav");
    }

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
}