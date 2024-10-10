package Processors;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.*;

public class CommandProcessor {

    private Map<String, Runnable> commandlookup;

    public CommandProcessor(){
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
        }

    }

    /**
     * InnerCommandProcessor
     */
    public class InnerCommandProcessor {

        void ventilationClose() {
            System.out.println("ventilation closed!");
        }
        
        void ventilationDiagnostics() {

        }

        void ventilationPump() {

        }
        
        void ventilationRefill() {

        }

        void ventilationWeld() {

        }

        void ventilationOpen() {

        }

        void engineDiagnostics() {

        }

        void engineStop() {

        }

        void engineRefuel() {

        }

        void engineStart() {

        }


        void coolantRead() {

        }

        void coolantRegulator() {

        }

        void coolantSet() {

        }

        void resourcesScan() {

        }

        void resourcesMine() {

        }

    }

}
