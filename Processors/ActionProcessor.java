package Processors;

import javax.swing.*;

import Handlers.visualHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ActionProcessor {

    static long prevtime = System.currentTimeMillis();
    static long deltaTime = 0;
    static float totalTime = 0;

    static float updateCount = 5;
    
    static Random random;

    private static Timer timer;

    static {
        random = new Random(System.currentTimeMillis());
        timer = new Timer(68, new ActionListener() {
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
        System.out.println("there is a problem with the ships ventilation");
        visualHandler.changeLight(true, 0);



    }

    public static void temperatureAction() {
        System.out.println("there is a problem with the ships temperature");
        visualHandler.changeLight(true, 1);
    }

    public static void fuelAction() {
        System.out.println("there is a problem with the ships fuel");
        visualHandler.changeLight(true, 2);

    }

    public static void oreAction() {

    }

    private static void update() {
        
        long time = System.currentTimeMillis();
        deltaTime = time - prevtime;
        prevtime = time;

        randomActionGenerator(deltaTime);
    }

    private static void randomActionGenerator(long deltaTime) {

        double deltaTimeScaled = deltaTime;

        float remainingTime = 1000 - totalTime;

        // The rate of increase diminishes as totalTime gets closer to MAX_TIME
        float increase = (float) (remainingTime * (1 - Math.exp(-deltaTimeScaled / 100000.0)));  // Exponential decay formula

        totalTime += increase;

        // Ensure totalTime doesn't exceed MAX_TIME
        if (totalTime > 1000) {
            totalTime = 1000;
        }

        //System.out.println("Total Time: " + totalTime + " " + deltaTimeScaled);

        if (updateCount == 10){
            updateCount = 0;

            float randomFloat = random.nextFloat() * 10000;

            if (totalTime > randomFloat){
                int randomInt = random.nextInt(3);

                switch(randomInt) {
                    case 0:
                        temperatureAction();
                    break;
                    case 1:
                        ventilationAction();
                    break;
                    case 2:
                        fuelAction();
                    break;
                }
            }
        } else{
            updateCount++;
        }

    }
}
    
