package main;

import enums.BaudRate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Command;
import models.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jerry on 4/12/2018.
 */
public class SettingsPaneController {
    @FXML
    private ToggleGroup baudRateGroup;

    @FXML
    private RadioButton radioButton38400;

    @FXML
    RadioButton radioButton9600;

    @FXML
    RadioButton radioButton2400;

    @FXML
    private ChoiceBox<String> controllerTypeChoiceBox;

    @FXML
    private TextField pollingComandTextField1;

    @FXML
    private TextField pollingComandTextField2;

    @FXML
    private TextField pollingComandTextField3;

    @FXML
    private CheckBox chartCheckBox1;

    @FXML
    private CheckBox chartCheckBox2;

    @FXML
    private CheckBox chartCheckBox3;


    public void initialize() {

        System.out.println("SettingsPaneController initialize called");
        pollingComandTextField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                chartCheckBox1.setSelected(false);
            }
        });
        pollingComandTextField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                chartCheckBox2.setSelected(false);
            }
        });
        pollingComandTextField3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){
                chartCheckBox3.setSelected(false);
            }
        });
    }


    public void initializeSettingsValues(Settings setting) {


        System.out.println("initializing settings pane");

        //get values from settings object and set in dialog pane
        if (setting.getBaudrate().equalsIgnoreCase(BaudRate.BAUD_RATE_2400.getBaudrate())) {
            radioButton2400.setSelected(true);
        } else if
                (setting.getBaudrate().equalsIgnoreCase(BaudRate.BAUD_RATE_9600.getBaudrate())) {
            radioButton9600.setSelected(true);
        } else if
                (setting.getBaudrate().equalsIgnoreCase(BaudRate.BAUD_RATE_38400.getBaudrate())) {
            radioButton38400.setSelected(true);
        }

        controllerTypeChoiceBox.setValue(setting.getControllerType());
        pollingComandTextField1.setText(setting.getOptionalCommandList().get(0).getCommandName());
        pollingComandTextField2.setText(setting.getOptionalCommandList().get(1).getCommandName());
        pollingComandTextField3.setText(setting.getOptionalCommandList().get(2).getCommandName());
        chartCheckBox1.setSelected(setting.getOptionalCommandList().get(0).isIsCharted());
        chartCheckBox2.setSelected(setting.getOptionalCommandList().get(1).isIsCharted());
        chartCheckBox3.setSelected(setting.getOptionalCommandList().get(2).isIsCharted());
    }


    public void process(Settings setting) {

        String baudRate = (String) baudRateGroup.getSelectedToggle().getUserData();
        String controllerType = controllerTypeChoiceBox.getValue();

        setting.setControllerType(controllerType);
        setting.setBaudrate(baudRate);
        List<Command> commands = new ArrayList<>();
        //if textfields empty, then uncheck chart checkbox
        if (pollingComandTextField1.getText().isEmpty()) chartCheckBox1.setSelected(false);
        if (pollingComandTextField2.getText().isEmpty()) chartCheckBox2.setSelected(false);
        if (pollingComandTextField3.getText().isEmpty()) chartCheckBox3.setSelected(false);
        //add commands to arraylist and store into settings
        commands.add(new Command(pollingComandTextField1.getText(),chartCheckBox1.isSelected()));
        commands.add(new Command(pollingComandTextField2.getText(),chartCheckBox2.isSelected()));
        commands.add(new Command(pollingComandTextField3.getText(),chartCheckBox3.isSelected()));
        setting.setOptionalCommandList(commands);
        //store settings to pref file
        setting.storeSettingsToPrefs();
    }
}
