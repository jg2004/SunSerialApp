package main;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Command;
import models.PollingCommandList;

public class PollingCommandDialogController {
    @FXML
    private TextField pollingComandTextField1;
    @FXML
    private TextField pollingComandTextField2;
    @FXML
    private TextField pollingComandTextField3;
    @FXML
    private TextField pollingComandTextField4;
    @FXML
    private TextField pollingComandTextField5;
    @FXML
    private TextField pollingComandTextField6;
    @FXML
    private TextField pollingComandTextField7;
    @FXML
    private TextField pollingComandTextField8;
    @FXML
    private CheckBox chartCheckBox1;
    @FXML
    private CheckBox chartCheckBox2;
    @FXML
    private CheckBox chartCheckBox3;
    @FXML
    private CheckBox chartCheckBox4;
    @FXML
    private CheckBox chartCheckBox5;
    @FXML
    private CheckBox chartCheckBox6;
    @FXML
    private CheckBox chartCheckBox7;
    @FXML
    private CheckBox chartCheckBox8;

    @FXML
    public void initialize() {
        System.out.println("PollingCommandDialogController initialize called");
    }

    public void initializeValues(ObservableList<Command> commandObjectObservableList) {
        System.out.println("initializing DialogController");

        if (commandObjectObservableList.size() > 0) {
            pollingComandTextField1.setText(commandObjectObservableList.get(0).getCommandName());
            chartCheckBox1.setSelected(commandObjectObservableList.get(0).isIsCharted());
        }

        if (commandObjectObservableList.size() > 1) {
            pollingComandTextField2.setText(commandObjectObservableList.get(1).getCommandName());
            chartCheckBox2.setSelected(commandObjectObservableList.get(1).isIsCharted());
        }

        if (commandObjectObservableList.size() > 2) {
            pollingComandTextField3.setText(commandObjectObservableList.get(2).getCommandName());
            chartCheckBox3.setSelected(commandObjectObservableList.get(2).isIsCharted());
        }

        if (commandObjectObservableList.size() > 3) {
            pollingComandTextField4.setText(commandObjectObservableList.get(3).getCommandName());
            chartCheckBox4.setSelected(commandObjectObservableList.get(3).isIsCharted());
        }

        if (commandObjectObservableList.size() > 4) {
            pollingComandTextField5.setText(commandObjectObservableList.get(4).getCommandName());
            chartCheckBox5.setSelected(commandObjectObservableList.get(4).isIsCharted());
        }

        if (commandObjectObservableList.size() > 5) {
            pollingComandTextField6.setText(commandObjectObservableList.get(5).getCommandName());
            chartCheckBox6.setSelected(commandObjectObservableList.get(5).isIsCharted());
        }

        if (commandObjectObservableList.size() > 6) {
            pollingComandTextField7.setText(commandObjectObservableList.get(6).getCommandName());
            chartCheckBox7.setSelected(commandObjectObservableList.get(6).isIsCharted());
        }

        if (commandObjectObservableList.size() > 7) {
            pollingComandTextField8.setText(commandObjectObservableList.get(7).getCommandName());
            chartCheckBox8.setSelected(commandObjectObservableList.get(7).isIsCharted());
        }
    }

    public PollingCommandList getResult(ObservableList<Command> commandObjectObservableList) {

        commandObjectObservableList.clear();

        if (!pollingComandTextField1.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField1.getText(), chartCheckBox1.isSelected()));
        }
        if (!pollingComandTextField2.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField2.getText(), chartCheckBox2.isSelected()));
        }

        if (!pollingComandTextField3.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField3.getText(), chartCheckBox3.isSelected()));
        }
        if (!pollingComandTextField4.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField4.getText(), chartCheckBox4.isSelected()));
        }
        if (!pollingComandTextField5.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField5.getText(), chartCheckBox5.isSelected()));
        }
        if (!pollingComandTextField6.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField6.getText(), chartCheckBox6.isSelected()));
        }

        if (!pollingComandTextField7.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField7.getText(), chartCheckBox7.isSelected()));
        }
        if (!pollingComandTextField8.getText().isEmpty()) {
            commandObjectObservableList.add(new Command(pollingComandTextField8.getText(), chartCheckBox8.isSelected()));
        }


        return new PollingCommandList(commandObjectObservableList);
    }
}
