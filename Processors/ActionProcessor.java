package Processors;

import javax.swing.*;

import Handlers.ScoreHandler;
import Handlers.terminalHandler;
import Handlers.visualHandler;
import Processors.CommandProcessor.CoolantState;
import Processors.CommandProcessor.EngineState;
import Processors.CommandProcessor.VentilationState;

import java.util.Random;
/**
 * Class for managing the 4 types of problems/actions that can be happening in the game.
 */
public class ActionProcessor {

    public static Boolean ventilationProblem = false;
    public static Boolean coolantProblem = false;
    public static Boolean engineProblem = false;

    static long prevtime = System.currentTimeMillis();
    static long deltaTime = 0;
    static float totalTime = 100;
    static float updateCount = 9;
    static Random random;

    private static Timer timer;

    private static int firstActions = 0;

    private static CommandProcessor myCommandProcessor;
    private static terminalHandler myTerminalHandler;
    private static SoundProcessor mySoundProcessor = new SoundProcessor();

    static {
        random = new Random(System.currentTimeMillis());
        timer = new Timer(68, e -> update());
    }


    public static void startActions(CommandProcessor myCommandProcessorPassed) {
        myCommandProcessor = myCommandProcessorPassed;
        timer.start();
    }

    public static void stopActions() {
        timer.stop();
    }


    /**
     * manages the ventilation problems and resolves possible edge-cases.
     */
    public static void ventilationAction() {
        ventilationProblem = true;
        System.out.println("there is a problem with the ships ventilation");
        visualHandler.changeLight(true, 1);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.ventilationState = VentilationState.NONE;



    }

    /**
     * manages the temperature problems and resolves possible edge-cases.
     */
    public static void temperatureAction() {
        coolantProblem = true;
        myCommandProcessor.temperature = new Random().nextInt(61) - 10;
        System.out.println("there is a problem with the ships temperature");
        visualHandler.changeLight(true, 0);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.coolantState = CoolantState.NONE;
    }

    /**
     * manages the fuel problems and resolves possible edge-cases.
     */
    public static void fuelAction() {
        engineProblem = true;
        myCommandProcessor.EnginePercentage = new Random().nextInt(100) + 1;
        System.out.println("there is a problem with the ships fuel");
        visualHandler.changeLight(true, 2);
        mySoundProcessor.playProblemSound();
        myCommandProcessor.engineState = EngineState.NONE;

    }

    /**
     * update loop for the game such as the increasing change for a problem to happen.
     */
    private static void update() {
        
        long time = System.currentTimeMillis();
        deltaTime = time - prevtime;
        prevtime = time;

        randomActionGenerator(deltaTime);
    }

    /**
     * generetes a random action after a random period of time, the longer the user is in the game the more problems will occur.
     * @param deltaTime
     */
    private static void randomActionGenerator(long deltaTime) {

        double deltaTimeScaled = deltaTime;

        float remainingTime = 1000 - totalTime;

        // The rate of increase diminishes as totalTime gets closer to MAX_TIME
        float increase = (float) (remainingTime * (1 - Math.exp(-deltaTimeScaled / 1000000.0)));  // Exponential decay formula

        totalTime += increase;

        // Ensure totalTime doesn't exceed MAX_TIME
        if (totalTime > 1000) {
            totalTime = 1000;
        }

        System.out.println("Total Time: " + totalTime + " " + deltaTimeScaled);

        if (updateCount == 10){
            updateCount = 0;
            
            firstActions++;

            if (firstActions == 3 ) {
                executeAction();
                return;
            }

            float randomFloat = random.nextFloat() * 10000;

            if (totalTime > randomFloat){
                executeAction();
            }
        } else{
            updateCount++;
        }

    }

    /**
     * executes a random action based on random number generation
     */
    private static void executeAction() {
        int randomInt = random.nextInt(3);

        if (coolantProblem && ventilationProblem && engineProblem) {

            return;
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

    public static void getCommandProcesor(CommandProcessor myCommandProcessorPassed) {
        myCommandProcessor = myCommandProcessorPassed;
        myTerminalHandler = myCommandProcessor.myTerminalHandler;
    }

    /**
     * method for managing what the game must do when you lose, which shows you your totalscore etc...
     */
    public static void lose() {

        stopActions();

        ScoreHandler.stopOxygenCounting();
        ScoreHandler.stopDepthCounting();


        float totalscore = (ScoreHandler.score * ScoreHandler.depth) / 100;

        myTerminalHandler.addToTerminal("The mining operation failed... Total Score: " + totalscore);
        myTerminalHandler.addToTerminal("");
        myTerminalHandler.addToTerminal("To shutdown the game type: 'system.shutdown()'");
    }

}
    
