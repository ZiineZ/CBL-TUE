package Processors;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.*;

import Handlers.ScoreHandler;
import Handlers.terminalHandler;

public class CommandProcessor {

    terminalHandler myTerminalHandler;

    private Map<String, Runnable> commandlookup;

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

        
    }
    
    public void process(String command) {
        String lowercaseCommand = command.toLowerCase();
        //TODO save additional arguments given in brackets to a temporary variable and remove them from the expression.
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(lowercaseCommand);
        String arguments;

        if(matcher.find()) {
            arguments = matcher.group(1);
        }

        String finalcommand = lowercaseCommand.replaceAll("\\(.*?\\)", "()");
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

        void ventilationClose() {
            System.out.println("Ventilation system closed to prevent oxygen loss!");
        }

        void ventilationDiagnostics() {
            System.out.println("Diagnosing ventilation system... Possible issues: clogged pipes, empty oxygen tank, or leak.");
        }

        void ventilationPump() {
            System.out.println("Applying pressure to clear blockages in the ventilation pipes!");
            ScoreHandler.scoreCount(100);
        }

        void ventilationRefill() {
            System.out.println("Refilling oxygen tank to restore the supply!");
            ScoreHandler.scoreCount(100);
        }

        void ventilationWeld() {
            System.out.println("Welding the ventilation pipes to fix the leak!");
            ScoreHandler.scoreCount(100);
        }

        void ventilationOpen() {
            System.out.println("Ventilation system reopened, restoring airflow and oxygen to the ship.");
        }

        void engineDiagnostics() {
            System.out.println("Checking fuel level... displaying percentage (0%-100%).");
        }

        void engineStop() {
            System.out.println("Engine stopped. Safe to refuel.");
        }

        void engineRefuel() {
            System.out.println("Refueling the engine... Make sure not to overfuel.");
            ScoreHandler.scoreCount(200);
        }

        void engineStart() {
            System.out.println("Engine restarted. Ready for operation.");
        }

        void coolantRead() {
            System.out.println("Reading coolant system temperature...");
        }

        void coolantRegulator() {
            System.out.println("Regulating coolant and heat pipes to balance the temperature...");
        }

        void coolantSet() {
            System.out.println("Setting coolant and heat flow percentages to stabilize the system.");
            ScoreHandler.scoreCount(100);
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

    }
}

