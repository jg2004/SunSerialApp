package models;

import com.fazecast.jSerialComm.SerialPort;
import enums.ControllerType;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * Created by Jerry on 5/7/2018.
 */
public class EC1xDevice extends Device {
    public EC1xDevice(SerialPort comPort, Settings settings) {
        super(comPort, settings);

        commandList.add("CHAM?\n");
        commandList.add("UCHAN?\n");
        commandList.add("RATE?\n");
        commandList.add("WAIT?\n");
        commandList.add("SET?\n");

        //add additional commands from settings object

        // TODO: 5/8/2018
        List<Command> optionalCommands = settings.getOptionalCommandList();
        System.out.println("optionalCommands size: " + optionalCommands.size());
        for (Command command: optionalCommands){
            commandList.add(command.getCommandName());
        }




        //create a list of responses corresponding to each command
        for (int i = 0; i < 8; i++) {
            responseList.add(new SimpleStringProperty("###"));
        }

        labelList.add(new SimpleStringProperty("CHAM:"));
        labelList.add(new SimpleStringProperty("USER:"));
        labelList.add(new SimpleStringProperty("RATE:"));
        labelList.add(new SimpleStringProperty("WAIT:"));
        labelList.add(new SimpleStringProperty("SET:"));
        labelList.add(new SimpleStringProperty(""));
        labelList.add(new SimpleStringProperty(""));
        labelList.add(new SimpleStringProperty(""));
        for (int i = 5; i < commandList.size() ; i++) {

            labelList.get(i).set(commandList.get(i).substring(0, commandList.get(i).length() - 2)+":");

            //this adds labelList for the extra optional commmands i.e C3?:
        }
    }
}
