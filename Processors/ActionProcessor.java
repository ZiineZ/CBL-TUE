package Processors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionProcessor {

    static long prevtime = 0;

    private static Timer timer;

    static {
        // Initialize the timer in a static block
        timer = new Timer(17, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update(); // Update method must also be static
            }
        });
    }


    public static void startActions() {
        timer.start();
    }

    public static void stopActions() {
        timer.stop();
    }


    
    public static void ventilationAction() {

    }

    public static void temperatureAction() {

    }

    public static void fuelAction() {

    }

    public static void oreAction() {

    }

    private static void update() {
        
        long time = System.currentTimeMillis();
        System.out.println(time - prevtime);
        prevtime = time;
    }
    
}
