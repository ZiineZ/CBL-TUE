package Processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.*;

import javax.swing.SwingUtilities;

import Handlers.ScoreHandler;
import Handlers.terminalHandler;
import Handlers.visualHandler;



public class CommandProcessor {

    CommandProcessor myCommandProcessor = this;

    public enum VentilationState {
        NONE, CLOSED, DIAGNOSTICS_RUN, CLOGGED, EMPTY_TANK, LEAK, FIXED
    }

    

    public enum EngineState {
        NONE, DIAGNOSTICS_RUN, STOPPED, REFUELED, FIXED
    }

    public enum CoolantState {
        NONE, TEMPERATURE_CHECKED, REGULATED, SET, FIXED
    }

    public enum ResourceState {
        NONE, SCANNED, MINED
    }

    boolean started = false;

    terminalHandler myTerminalHandler;
    SoundProcessor mySoundProcessor = new SoundProcessor();

    private Map<String, Runnable> commandlookup;

    public VentilationState ventilationState = VentilationState.NONE;
    public EngineState engineState = EngineState.NONE;
    public CoolantState coolantState = CoolantState.NONE;
    public ResourceState resourceState = ResourceState.NONE;

    public int EnginePercentage = 0;
    public int temperature = 0;
    public int coolantregulatornumber = 0;
    public int coolantamount;
    public int heatingamount;
    String arguments = "";

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CommandProcessor(terminalHandler terminal){

        myTerminalHandler = terminal;
        ActionProcessor.getCommandProcesor(myCommandProcessor);

        commandlookup = new HashMap();
        InnerCommandProcessor icp = new InnerCommandProcessor();

        commandlookup.put("system.ventilation.close()", icp::ventilationClose);
        commandlookup.put("system.repairtool.diagnostics(ventilation)", icp::ventilationDiagnostics);
        commandlookup.put("system.pressurecontroller.pump(ventilation)", icp::ventilationPump);
        commandlookup.put("system.supply.refill(oxygen)", icp::ventilationRefill);
        commandlookup.put("system.repairtool.weld(ventilation)", icp::ventilationWeld);
        commandlookup.put("system.ventilation.open()", icp::ventilationOpen);

        commandlookup.put("system.repairtool.diagnostics(fuel)", icp::engineDiagnostics);
        commandlookup.put("system.engine.stop()", icp::engineStop);
        commandlookup.put("system.engine.refuel()", icp::engineRefuel); 
        commandlookup.put("system.engine.start()", icp::engineStart);

        commandlookup.put("system.coolant.read()", icp::coolantRead);
        commandlookup.put("system.coolant.regulator()", icp::coolantRegulator);
        commandlookup.put("system.coolant.set()", icp::coolantSet);

        commandlookup.put("system.resources.scan()", icp::resourcesScan);
        commandlookup.put("system.resources.mine()", icp::resourcesMine);

        commandlookup.put("system.help.temperature()", icp::helpTemperature);
        commandlookup.put("system.help.ventilation()", icp::helpVentilation);
        commandlookup.put("system.help.fuel()", icp::helpFuel);
        commandlookup.put("system.help.mine()", icp::helpMine);

        commandlookup.put("system.getstatus()", icp::getState);

        commandlookup.put("system.reboot()", icp::reboot);

        commandlookup.put("clear()", icp::clear);

        commandlookup.put("start()", icp::start);
        commandlookup.put("stop()", icp::stop);
    }
    
    public void process(String command) {

        String lowercaseCommand = command.toLowerCase();
        String finalcommand = "";
        if (lowercaseCommand.contains("(") && lowercaseCommand.contains(")")) {
            arguments = lowercaseCommand.substring(lowercaseCommand.indexOf("(")+1, lowercaseCommand.indexOf(")"));
        }

        finalcommand = lowercaseCommand.replaceAll("\\(.*?\\)", "()");

        if (arguments.equals("ventilation") || arguments.equals("fuel")) {
            finalcommand = lowercaseCommand;
        }
        
        Runnable function = commandlookup.get(finalcommand);
        
        if (function != null){
            function.run();
        }
        else {
            System.out.println("command not found!");
            myTerminalHandler.addToTerminal("command not found!");
        }

    }

    /**
     * InnerCommandProcessor
     */
    public class InnerCommandProcessor {

        //TODO:
        //Make a command where the user can see what condition certain systems are in!

        void ventilationClose() {
            if (ventilationState == VentilationState.NONE) {

                ventilationState = VentilationState.CLOSED;
                

                System.out.println(ventilationState);

                myTerminalHandler.addToTerminal("Ventilation system closed. Oxygen loss imminent.");

                ScoreHandler.startOxygenCounting();
                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("Cannot close ventilation systems in current state.");
            }
        }

        void ventilationDiagnostics() {
            
            System.out.println(ventilationState);

            if (ventilationState == VentilationState.CLOSED) {

                String issueText = "";

                ventilationState = VentilationState.DIAGNOSTICS_RUN;
                int issue = new Random().nextInt(3);
                switch (issue) {
                    case 0 -> {ventilationState = VentilationState.CLOGGED; issueText = "clogged pipes.";}
                    case 1 -> {ventilationState = VentilationState.EMPTY_TANK; issueText = "empty oxygen tank."; }
                    case 2 -> {ventilationState = VentilationState.LEAK; issueText = "leak in pipe.";}
                }

                myTerminalHandler.addToTerminal("Diagnostics completed. Identified issue: " + issueText);

                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("Cannot run diagnostics in current state.");
            }
        }

        void ventilationPump() {
            if (ventilationState == VentilationState.CLOGGED) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Ventilation clog cleared.");

                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("No clog to pump.");
            }
        }

        void ventilationRefill() {
            if (ventilationState == VentilationState.EMPTY_TANK) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Oxygen tank refilled.");

                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("No empty tank to refill.");
            }
        }

        void ventilationWeld() {
            if (ventilationState == VentilationState.LEAK) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Leak welded.");

                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("No leak to weld.");
            }
        }

        void ventilationOpen() {
            if (ventilationState == VentilationState.FIXED) {
                ventilationState = VentilationState.NONE;
                ActionProcessor.ventilationProblem = false;
                visualHandler.changeLight(false, 1);
                mySoundProcessor.playSolvedSound();
                myTerminalHandler.addToTerminal("Ventilation system reopened.");

                ScoreHandler.stopOxygenCounting();
                ScoreHandler.scoreCount(100);

            } else {
                myTerminalHandler.addToTerminal("Cannot open ventilation system in current state.");
            }
        }

        void engineDiagnostics() {
            if (engineState == EngineState.NONE) {
                engineState = EngineState.DIAGNOSTICS_RUN;
                
                myTerminalHandler.addToTerminal("Engine fuel level: " + EnginePercentage + "%");

                ScoreHandler.scoreCount(50);

            } else {
                myTerminalHandler.addToTerminal("Cannt run diagnostics on the engine in current state.");
            }
        }

        void engineStop() {
            if (engineState == EngineState.DIAGNOSTICS_RUN) {
                engineState = EngineState.STOPPED;
                myTerminalHandler.addToTerminal("Engine stopped.");

                ScoreHandler.scoreCount(50);
                ScoreHandler.stopDepthCounting();

            } else {
                myTerminalHandler.addToTerminal("Cannot stop engine in current state.");
            }
        }

        void engineRefuel() {
            if (engineState == EngineState.STOPPED) {
                int userEnginePercentage = 100;
                try {
                    userEnginePercentage = Integer.parseInt(arguments);

                }
                catch (NumberFormatException e){
                    myTerminalHandler.addToTerminal("No refuel percentage given.");
                }

                if (userEnginePercentage == EnginePercentage) {
                    engineState = EngineState.REFUELED;
                    myTerminalHandler.addToTerminal("Engine succesfully refueled.");

                    ScoreHandler.scoreCount(50);

                } else {
                    myTerminalHandler.addToTerminal("Engine not properly refueled. Critical engine malfunction.");
                    ActionProcessor.lose();
                }

            } else {
                myTerminalHandler.addToTerminal("Cannot refuel engine in current state.");
            }
        }

        void engineStart() {
            if (engineState == EngineState.REFUELED) {
                engineState = EngineState.NONE;
                ActionProcessor.engineProblem = false;
                visualHandler.changeLight(false, 2);
                myTerminalHandler.addToTerminal("Engine started succesfully");

                ScoreHandler.scoreCount(100);
                ScoreHandler.startDepthCounting();

                mySoundProcessor.playSolvedSound();
            } else {
                myTerminalHandler.addToTerminal("Cannot start engine in current state");
            }
        }

        void coolantRead() {
            if (coolantState == CoolantState.NONE) {
                coolantState = CoolantState.TEMPERATURE_CHECKED;
                myTerminalHandler.addToTerminal("The temperature is: " + temperature + "°C");

                ScoreHandler.scoreCount(50);
            } else {
                myTerminalHandler.addToTerminal("Unable to read the temperature in the current state.");
            }
        }

        void coolantRegulator() {
            if (coolantState == CoolantState.TEMPERATURE_CHECKED) {
                int userTemperature = 0;

                try {
                    userTemperature = Integer.parseInt(arguments);

                } catch (NumberFormatException e) {
                    myTerminalHandler.addToTerminal("No temperature is properly inputted.");
                    return;
                }
                
                coolantState = CoolantState.REGULATED;

               coolantregulatornumber = (6 * 6 * 6 * (userTemperature + 10)) ^ (6 * 6 * (userTemperature + 10)) ^ (6 * (coolantregulatornumber + 10));
               coolantamount = (int)(coolantregulatornumber / 100);
               heatingamount = (int)(coolantregulatornumber % 100);

               ScoreHandler.scoreCount(50);

               myTerminalHandler.addToTerminal("The following numbers regulate the temperature:\nCoolant: " + coolantamount + ". Heating: " + heatingamount + ".");
            } else {
                myTerminalHandler.addToTerminal("Cannot cannot get regulated numbers in the current state.");
            }
        }

        void coolantSet() {
            if (coolantState == CoolantState.REGULATED) {
                int userCoolantAmount = 0;
                int userHeatingAmount = 0;

                try {
                    String[] parts = arguments.split(",\\s*");

                    userCoolantAmount = Integer.parseInt(parts[0]);
                    userHeatingAmount = Integer.parseInt(parts[1]);

                } catch (NumberFormatException e) {
                    myTerminalHandler.addToTerminal("The regulator numbers are not properly inputted.");
                    return;
                }

                if (userCoolantAmount == coolantamount && userHeatingAmount == heatingamount) {
                    coolantState = CoolantState.NONE;
                    ActionProcessor.coolantProblem = false;
                    visualHandler.changeLight(false, 0);
                    mySoundProcessor.playSolvedSound();

                    ScoreHandler.scoreCount(100);

                    myTerminalHandler.addToTerminal("Temperature succesfully regulated.");
                } else {
                    myTerminalHandler.addToTerminal("Failed to regulate temperature, regulator numbers incorrect.");
                }
            } else {
                myTerminalHandler.addToTerminal("Cannot set coolant flow in current state.");
            }
        }

        void resourcesScan() {
            if (resourceState == ResourceState.NONE) {
                resourceState = ResourceState.SCANNED;
                visualHandler.changeLight(true, 3);
                int depth = ScoreHandler.searchForOre();
                myTerminalHandler.addToTerminal("ore found at: " + depth + " km.");
                mySoundProcessor.playProblemSound();
            }
            
        }

        void resourcesMine() {
            if(ScoreHandler.mineOre()) {
                resourceState = ResourceState.NONE;
                visualHandler.changeLight(false, 3);
                mySoundProcessor.playSolvedSound();
                int oreScore = new Random().nextInt(300, 1000);
                ScoreHandler.scoreCount(oreScore);
                myTerminalHandler.addToTerminal("Ore mining succesfull! +" + oreScore + " score gained!");
            }
            else{
                resourceState = ResourceState.NONE;
                visualHandler.changeLight(false, 3);
                myTerminalHandler.addToTerminal("Ore mining failed.");
            }
        }

        void clear() {
            myTerminalHandler.clear();
        }

        void start() {
            if (started) {
                return;
            }

            ScoreHandler.startTimeCounting();
            ScoreHandler.startDepthCounting();
            ActionProcessor.startActions(myCommandProcessor);
            mySoundProcessor.playBackgroundMusic();
        }

        void stop() {
            ActionProcessor.stopActions();
        }

        void helpTemperature() {
            String text = "To manage the temperature of your ship's coolant system, follow these steps:\n" +
                    "1. Check the current temperature:\n" +
                    "   `System.coolant.read()`\n" +
                    "   Displays the current coolant temperature.\n" +
                    "2. Adjust coolant and heat pipes:\n" +
                    "   `System.coolant.regulator({temperature})`\n" +
                    "   Calculates the required heat and coolant percentages.\n" +
                    "3. Set the heat and coolant percentages:\n" +
                    "   `System.coolant.set({heat percentage}, {coolant percentage})`\n" +
                    "   Adjusts the flow rates to stabilize the system.";

            myTerminalHandler.addToTerminal(text);
        }

        void helpVentilation() {
            String text = "To resolve a ventilation problem, follow these steps:\n" +
                    "1. Shut off the ventilation system:\n" +
                    "   `System.ventilation.close()`\n" +
                    "   Stops oxygen from escaping.\n" +
                    "2. Run diagnostics to identify the problem:\n" +
                    "   `System.repairtool.diagnostics(ventilation)`\n" +
                    "   Checks for issues in the ventilation system.\n" +
                    "3. Address the specific issue based on the diagnostics:\n" +
                    "   - If pipes are clogged:\n" +
                    "     `System.pressurecontroller.pump(ventilation)`\n" +
                    "     Clears blockages.\n" +
                    "   - If the oxygen tank is empty:\n" +
                    "     `System.supply.refill(oxygen)`\n" +
                    "     Refills the oxygen tank.\n" +
                    "   - If there is a leak:\n" +
                    "     `System.repairtool.weld(ventilation)`\n" +
                    "     Repairs the leak.\n" +
                    "4. Reopen the ventilation system:\n" +
                    "   `System.ventilation.open()`\n" +
                    "   Restores airflow.";

            myTerminalHandler.addToTerminal(text);
        }

        void helpFuel() {
            String text = "To safely refuel your ship, follow these steps:\n" +
                    "1. Check the fuel level:\n" +
                    "   `System.repairtool.diagnostics(fuel)`\n" +
                    "   Displays fuel level as a percentage.\n" +
                    "2. Stop the engine before refueling:\n" +
                    "   `System.engine.stop()`\n" +
                    "   Shuts down the engine.\n" +
                    "3. Refuel the engine:\n" +
                    "   `System.engine.refuel({percentage})`\n" +
                    "   Specify the amount of fuel to add.\n" +
                    "4. Restart the engine:\n" +
                    "   `System.engine.start()`\n" +
                    "   Restarts the engine for continued operation.";

            myTerminalHandler.addToTerminal(text);
        }

        void helpMine() {
            String text = "To mine ores and get extra points, follow these steps:\n" +
            "1. Scan for ore nearby:\n" +
            "   'system.resources.scan()'\n" +
            "2. Mine the ore when you are 500-0 meters infront of it.\n" +
            "   'system.resources.mine()'";

            myTerminalHandler.addToTerminal(text);
        }

        void getState() {
        myTerminalHandler.addToTerminal("Current System States:");
        myTerminalHandler.addToTerminal("Ventilation State: " + ventilationState);
        myTerminalHandler.addToTerminal("Engine State: " + engineState);
        myTerminalHandler.addToTerminal("Coolant State: " + coolantState);
        myTerminalHandler.addToTerminal("Resource State: " + resourceState);
        }

        void reboot() {
            System.exit(0);
        }

    }
}

