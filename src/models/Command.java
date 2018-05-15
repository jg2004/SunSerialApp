package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Jerry on 4/4/2018.
 */
public class Command {

    StringProperty commandName;
    BooleanProperty isCharted;


    // Constructor
    public Command(String commandName, boolean isCharted) {

        this.commandName = new SimpleStringProperty(commandName.toUpperCase());
        this.isCharted = new SimpleBooleanProperty(isCharted);

    }

    //    getters and setters

    public String getCommandName() {
        return commandName.get();
    }

    public StringProperty commandNameProperty() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName.set(commandName);
    }

    public boolean isIsCharted() {
        return isCharted.get();
    }

    public BooleanProperty isChartedProperty() {
        return isCharted;
    }

    public void setIsCharted(boolean isCharted) {
        this.isCharted.set(isCharted);
    }

}