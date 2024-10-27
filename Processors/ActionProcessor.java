package Processors;

import javax.swing.*;

import Handlers.visualHandler;
import Processors.CommandProcessor.CoolantState;
import Processors.CommandProcessor.EngineState;
import Processors.CommandProcessor.VentilationState;

import java.util.Random;

public class ActionProcessor {

    public static Boolean ventilationProblem = false;
    public static Boolean coolantProblem = false;
    public static Boolean engineProblem = false;

    static long prevtime = System.currentTimeMillis();
    static long deltaTime = 0;
    static float totalTime = 0;
    static float updateCount = 5;
    static Random random;

    private static Timer timer;

    private static CommandProcessor myCommandProcessor;
    private static SoundProcessor mySoundProcessor = new SoundProcessor();

    static {
        random = new Random(System.currentTimeMillis());
        timer = new Timer(68, e -> update());
    }


    public static void startActions(CommandProcessor myCommandProcessorPassed) {
        timer.start();
        myCommandProcessor = myCommandProcessorPassed;
    }

    public static void stopActions() {
        timer.stop();
    }


    
    public static void ventilationAction() {
        ventilationProblem = true;
        System.out.println("there is a problem with the ships ventilation");
        visualHandler.changeLight(true, 1);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.ventilationState = VentilationState.NONE;



    }

    public static void temperatureAction() {
        coolantProblem = true;
        myCommandProcessor.temperature = new Random().nextInt(61) - 10;
        System.out.println("there is a problem with the ships temperature");
        visualHandler.changeLight(true, 0);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.coolantState = CoolantState.NONE;
    }

    public static void fuelAction() {
        engineProblem = true;
        myCommandProcessor.EnginePercentage = new Random().nextInt(100) + 1;
        System.out.println("there is a problem with the ships fuel");
        visualHandler.changeLight(true, 2);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.engineState = EngineState.NONE;

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
                executeAction();
            }
        } else{
            updateCount++;
        }

    }

    private static void executeAction() {
        int randomInt = random.nextInt(3);

        if (coolantProblem && ventilationProblem && engineProblem) {
            //TODO TRIGGER GAME END!
        }
                    
        switch(randomInt) {
            case 0:
                if (!coolantProblem) {
                    temperatureAction();
                } else {
                    executeAction();
                }
            break;
            case 1:
                if (!ventilationProblem) {
                    ventilationAction();
                } else {
                    executeAction();
                }
            break;
            case 2:
                if (!engineProblem) {
                    fuelAction();
                } else {
                    executeAction();
                }
            break;
        }
    }
}
    
