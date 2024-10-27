package Processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.*;
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

        commandlookup.put("clear()", icp::clear);

        commandlookup.put("addscore()", icp::addscore);

        commandlookup.put("start()", icp::start);
        commandlookup.put("stop()", icp::stop);

    }
    
    public void process(String command) {

        String lowercaseCommand = command.toLowerCase();
        String finalcommand = "";

        arguments = lowercaseCommand.substring(lowercaseCommand.indexOf("(")+1, lowercaseCommand.indexOf(")"));
        
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

            } else {
                myTerminalHandler.addToTerminal("Cannot run diagnostics in current state.");
            }
        }

        void ventilationPump() {
            if (ventilationState == VentilationState.CLOGGED) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Ventilation clog cleared.");
            } else {
                myTerminalHandler.addToTerminal("No clog to pump.");
            }
        }

        void ventilationRefill() {
            if (ventilationState == VentilationState.EMPTY_TANK) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Oxygen tank refilled.");
            } else {
                myTerminalHandler.addToTerminal("No empty tank to refill.");
            }
        }

        void ventilationWeld() {
            if (ventilationState == VentilationState.LEAK) {
                ventilationState = VentilationState.FIXED;
                myTerminalHandler.addToTerminal("Leak welded.");
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
            } else {
                myTerminalHandler.addToTerminal("Cannot open ventilation system in current state.");
            }
        }

        void engineDiagnostics() {
            if (engineState == EngineState.NONE) {
                engineState = EngineState.DIAGNOSTICS_RUN;
                
                myTerminalHandler.addToTerminal("Engine fuel level: " + EnginePercentage + "%");
            } else {
                myTerminalHandler.addToTerminal("Cannt run diagnostics on the engine in current state.");
            }
        }

        void engineStop() {
            if (engineState == EngineState.DIAGNOSTICS_RUN) {
                engineState = EngineState.STOPPED;
                myTerminalHandler.addToTerminal("Engine stopped.");
                // TODO stop going down...
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
                } else {
                    myTerminalHandler.addToTerminal("Engine not properly refueled. Critical engine malfunction.");
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
                mySoundProcessor.playSolvedSound();
            } else {
                myTerminalHandler.addToTerminal("Cannot start engine in current state");
            }
        }

        void coolantRead() {
            if (coolantState == CoolantState.NONE) {
                coolantState = CoolantState.TEMPERATURE_CHECKED;
                myTerminalHandler.addToTerminal("The temperature is: " + temperature + "°C");
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
                    myTerminalHandler.addToTerminal("Temperature succesfully regulated.");
                } else {
                    myTerminalHandler.addToTerminal("Failed to regulate temperature, regulator numbers incorrect.");
                }
            } else {
                myTerminalHandler.addToTerminal("Cannot set coolant flow in current state.");
            }
        }

        void resourcesScan() {
            System.out.println("Scanning for ore deposits... Depth value detected.");
            ScoreHandler.searchForOre();
        }

        void resourcesMine() {
            System.out.println("Mining ore... Ensure you are within the correct depth range.");
            ScoreHandler.mineOre();
        }

        void clear() {
            myTerminalHandler.clear();
        }

        void addscore() {
            ScoreHandler.scoreCount(100);
        }

        void start() {
            System.out.println("Start!");
            ActionProcessor.ventilationAction();
        }

        void stop() {
            ActionProcessor.stopActions();
        }

    }
}

