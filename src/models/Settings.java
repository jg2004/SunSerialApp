package models;

import main.MainController;
import enums.BaudRate;
import enums.ControllerType;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import static util.Constants.*;

/**
 * Represents the state of the devices settings such as controller type, baudrate, etc.
 * Settings object is initialized in MainController and stored in stop method of MainController
 */
public class Settings {

    String baudrate;
    String controllerType;
    Preferences preferences;
    List<Command> optionalCommandList;

    public Settings(Class<? extends MainController> mainController) {
        preferences = Preferences.userNodeForPackage(mainController);
        optionalCommandList = new ArrayList<>();
        restorSettingsFromPrefs();

        System.out.println("Settings created...Preference location: " + preferences.absolutePath());
    }

    public String getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public List<Command> getOptionalCommandList() {
        return optionalCommandList;
    }

    public void setOptionalCommandList(List<Command> optionalCommandList) {
        this.optionalCommandList = optionalCommandList;
    }

    private void restorSettingsFromPrefs() {

        setControllerType(preferences.get(CONTROLLER_TYPE, ControllerType.EC1x.getType()));
        setBaudrate(preferences.get(BAUD_RATE, BaudRate.BAUD_RATE_9600.getBaudrate()));
        String optionalCommand1 = preferences.get(OPTIONAL_COMMAND_1, "");
        boolean optionalCommand1IsCharted = preferences.getBoolean(OPT_COMM_1_IS_CHARTED, false);
        String optionalCommand2 = preferences.get(OPTIONAL_COMMAND_2, "");
        boolean optionalCommand2IsCharted = preferences.getBoolean(OPT_COMM_2_IS_CHARTED, false);
        String optionalCommand3 = preferences.get(OPTIONAL_COMMAND_3, "");
        boolean optionalCommand3IsCharted = preferences.getBoolean(OPT_COMM_3_IS_CHARTED, false);
        optionalCommandList.add(new Command(optionalCommand1, optionalCommand1IsCharted));
        optionalCommandList.add(new Command(optionalCommand2, optionalCommand2IsCharted));
        optionalCommandList.add(new Command(optionalCommand3, optionalCommand3IsCharted));
    }

    public void storeSettingsToPrefs() {

        preferences.put(BAUD_RATE, baudrate);
        preferences.put(CONTROLLER_TYPE, controllerType);

        preferences.put(OPTIONAL_COMMAND_1, optionalCommandList.get(0).getCommandName());
        preferences.putBoolean(OPT_COMM_1_IS_CHARTED, optionalCommandList.get(0).isIsCharted());

        preferences.put(OPTIONAL_COMMAND_2, optionalCommandList.get(1).getCommandName());
        preferences.putBoolean(OPT_COMM_2_IS_CHARTED, optionalCommandList.get(1).isIsCharted());

        preferences.put(OPTIONAL_COMMAND_3, optionalCommandList.get(2).getCommandName());
        preferences.putBoolean(OPT_COMM_3_IS_CHARTED, optionalCommandList.get(2).isIsCharted());

    }
}