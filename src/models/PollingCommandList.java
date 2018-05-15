package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.prefs.Preferences;

/**
 * has a list of commands to poll
 */
public class PollingCommandList {

    private ObservableList<Command> commandObjectObservableList;


    //Constructor

    public PollingCommandList() {

        Preferences preferences = Preferences.userNodeForPackage(getClass());
        StringBuilder sbCommand = new StringBuilder();
        StringBuilder sbCommandToChart = new StringBuilder();

        commandObjectObservableList = FXCollections.observableArrayList();

        for (int i = 0; i < 8; i++) {
            sbCommand.append("command");
            sbCommandToChart.append("commandIsCharted");
            String commandName = preferences.get(sbCommand.append(i).toString(), "NONE");
            boolean isCharted = preferences.getBoolean(sbCommandToChart.append(i).toString(), false);
            if (!commandName.equals("NONE")) {
                commandObjectObservableList.add(new Command(commandName, isCharted));
            }
            sbCommand.delete(0, sbCommand.length());
            sbCommandToChart.delete(0, sbCommandToChart.length());
        }
    }

    public PollingCommandList(ObservableList<Command> commandObjectObservableList) {

        this.commandObjectObservableList = FXCollections.observableArrayList(commandObjectObservableList);

    }


    //getters and setters

    public ObservableList<Command> getCommandObjectObservableList() {
        return commandObjectObservableList;
    }

    public void setCommandObjectObservableList(ObservableList<Command> commandObjectObservableList) {
        this.commandObjectObservableList = commandObjectObservableList;
    }

    public void storeListToPrefs() {

        Preferences preferences = Preferences.userNodeForPackage(getClass());
        StringBuilder sbCommand = new StringBuilder();
        StringBuilder sbCommandToChart = new StringBuilder();

        for (int i = 0; i < 8; i++) {

            sbCommand.append("command");
            sbCommandToChart.append("commandIsCharted");
            if (i < commandObjectObservableList.size()) {
                Command command = commandObjectObservableList.get(i);
                preferences.put(sbCommand.append(i).toString(), command.getCommandName());
                preferences.putBoolean(sbCommandToChart.append(i).toString(), command.isIsCharted());
            } else {
                preferences.put(sbCommand.append(i).toString(), "NONE");
                preferences.putBoolean(sbCommandToChart.append(i).toString(), false);
            }

            sbCommand.delete(0, sbCommand.length());
            sbCommandToChart.delete(0, sbCommandToChart.length());
        }
    }
}
