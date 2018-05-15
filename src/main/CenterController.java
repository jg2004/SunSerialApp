package main;

import com.fazecast.jSerialComm.SerialPort;
import enums.ControllerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import models.Command;
import models.Device;
import models.PollingCommandList;
import models.Settings;
import services.GetComPortService;
import services.PollingService;

import java.io.IOException;
import java.util.Optional;

public class CenterController {

    private ObservableList<String> comPortDescriptionObservableList = FXCollections.observableArrayList();
    private ObservableList<SerialPort> serialPortObservableList = FXCollections.observableArrayList();
    private SerialPort mSelectedSerialPort;
    public static final String NO_COMM_PORTS = "NO COM PORTS";
    private Service comPortService;
    private PollingCommandList pollingCommandList;
    private Device device;
    private MainController mainController;
    private Settings settings;

    @FXML
    ComboBox<String> comboBoxComPortDescriptions;
    @FXML
    HBox progressHbox;
    @FXML
    Label progressLabel;
    @FXML
    Button refreshButton;
    @FXML
    ListView<Command> pollingCommandListView;
    @FXML
    private Label gridLabel_1;
    @FXML
    private Label gridLabel_2;
    @FXML
    private Label gridLabel_3;
    @FXML
    private Label gridLabel_4;
    @FXML
    private Label gridLabel_5;
    @FXML
    private Label gridLabel_6;
    @FXML
    private Label gridLabel_response_1;
    @FXML
    private Label gridLabel_response_2;
    @FXML
    private Label gridLabel_response_3;
    @FXML
    private Label gridLabel_response_4;
    @FXML
    private Label gridLabel_response_5;
    @FXML
    private Label gridLabel_response_6;
    @FXML
    private ToggleButton toggleButtonOnOff;
    @FXML
    private Button btnSendCommand;

    @FXML
    public void initialize() {
        System.out.println("CenterController, initialize called");


        initializeControls();
        initializeMenus();
        initializeServices();
        comPortService.restart();
    }

    @FXML
    void handleRefreshComPortList() {

        System.out.println("button clicked");
        comPortDescriptionObservableList.clear();
        comPortService.restart();
    }

    @FXML
    void handleSendButtonClicked(ActionEvent event) {

        System.out.println("send clicked");
        device.pollNextCommand();
    }


    @FXML
    void handleToggleOnOff(ActionEvent event) {

        boolean isPressed = toggleButtonOnOff.isSelected();

        System.out.println("settings: " + settings);

        if (isPressed) {

            System.out.println("toggle selected");
            String selectedPort = comboBoxComPortDescriptions.getSelectionModel().getSelectedItem();
            mSelectedSerialPort = getComPortFromDescription(selectedPort);
            device = Device.createDevice(settings, mSelectedSerialPort);
            gridLabel_1.textProperty().bind(device.getLabelList().get(0));
            gridLabel_2.textProperty().bind(device.getLabelList().get(1));
            gridLabel_3.textProperty().bind(device.getLabelList().get(2));
            gridLabel_4.textProperty().bind(device.getLabelList().get(3));
            gridLabel_5.textProperty().bind(device.getLabelList().get(4));


            PollingService pollingService = new PollingService(device);
            pollingService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                int commandIndex;

                @Override
                public void handle(WorkerStateEvent event) {
                    commandIndex = device.getCurrentPollingCommandIndex();
                    gridLabel_response_1.textProperty().bind(device.getResponseList().get(0));
                    gridLabel_response_2.textProperty().bind(device.getResponseList().get(1));
                    gridLabel_response_3.textProperty().bind(device.getResponseList().get(2));
                    gridLabel_response_4.textProperty().bind(device.getResponseList().get(3));
                    gridLabel_response_5.textProperty().bind(device.getResponseList().get(4));
                    if (commandIndex > 4) {
                        gridLabel_response_6.textProperty().bind(device.getResponseList().get(commandIndex));
                        gridLabel_6.textProperty().bind(device.getLabelList().get(commandIndex));
                    }
                    //inc counter to poll next command
                    device.incCommandCounter();

                }
            });
            pollingService.setOnReady(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    System.out.println(" set on ready called, unbinding properties");
                    gridLabel_response_1.textProperty().unbind();
                    gridLabel_response_2.textProperty().unbind();
                    gridLabel_response_3.textProperty().unbind();
                    gridLabel_response_4.textProperty().unbind();
                    gridLabel_response_5.textProperty().unbind();
                    gridLabel_response_6.textProperty().unbind();
                }
            });

            pollingService.setPeriod(Duration.seconds(1));
            pollingService.start();

            System.out.println("serial port: " + mSelectedSerialPort);

        } else {
            System.out.println("toggle not selected");
        }
    }


    private SerialPort getComPortFromDescription(String comPortDescription) {

        if (!comPortDescription.equalsIgnoreCase(NO_COMM_PORTS)) {


            for (SerialPort port : serialPortObservableList) {

                if (port.getSystemPortName().equalsIgnoreCase(comPortDescription)) {
                    System.out.println("port found: " + port.getSystemPortName());
                    return port;
                }
            }

        } else {
            System.out.println("no available com ports");
        }

        return null;
    }


    private void initializeServices() {

        System.out.println("initialize services called");
        comPortService = new GetComPortService(progressLabel, serialPortObservableList);

        comPortService.setOnRunning(event -> {

            refreshButton.setDisable(true);
            comboBoxComPortDescriptions.setDisable(true);
            System.out.println("service running! in thread " + Thread.currentThread().getName());
            progressHbox.setVisible(true);

        });
        comPortService.setOnSucceeded(event -> {
            System.out.println("service complete!");
            progressHbox.setVisible(false);
            refreshButton.setDisable(false);
            comboBoxComPortDescriptions.setDisable(false);
            populateSerialPortComboBoxDescriptions();
            if (!comboBoxComPortDescriptions.getItems().isEmpty()) {
                comboBoxComPortDescriptions.getSelectionModel().select(0);
            } else {
                System.out.println("combo box is empty");
            }

            String selectedPort = comboBoxComPortDescriptions.getSelectionModel().getSelectedItem();
            mSelectedSerialPort = getComPortFromDescription(selectedPort);
        });

    }

    private void populateSerialPortComboBoxDescriptions() {
        for (SerialPort port : serialPortObservableList) {
            comPortDescriptionObservableList.add(port.getSystemPortName());

        }
        if (comPortDescriptionObservableList.isEmpty()) comPortDescriptionObservableList.add(NO_COMM_PORTS);
    }

    private void initializeControls() {
        System.out.println("initializeControls called");
        Image image = new Image("images/refresh_black_24dp_1x.png");
        refreshButton.setGraphic(new ImageView(image));
        comboBoxComPortDescriptions.setItems(comPortDescriptionObservableList);

        pollingCommandList = new PollingCommandList();
//        pollingCommandListView.setItems(pollingCommandList.getCommandObjectObservableList());
//        pollingCommandListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Command, ObservableValue<Boolean>>() {
//            @Override
//            public ObservableValue<Boolean> call(Command param) {
//                return param.isChartedProperty();
//            }
//        }, new StringConverter<Command>() {
//            @Override
//            public String toString(Command object) {
//                return object.getCommandName();
//            }
//
//            @Override
//            public Command fromString(String string) {
//                return null;
//            }
//        }));


    }

    private void getAndDisplayComPorts() {

        //serialPortObservableList = SerialHelperClass.populateSerialPortList(serialPortObservableList);


//        for (SerialPort port : serialPortObservableList) {
//
//            useableComPortDescription.add(port.getSystemPortName());
//        }
//
//        if (useableComPortDescription.isEmpty()) {
//
//            useableComPortDescription.add(NO_COMM_PORTS);
//        }
    }

    @FXML
    void handleDisplayCommandObject() throws IOException {
        editPollingCommands();
    }

    private void editPollingCommands() throws IOException {

        System.out.println("handleDisplayCommandObject called");
        Dialog<PollingCommandList> dialog = new Dialog<>();
        DialogPane pane = new DialogPane();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("pollingCommandDialog.fxml"));
        Node paneRoot = loader.load();
        PollingCommandDialogController controller = loader.getController();

        //initialize textfields,check boxes with values from listview
        controller.initializeValues(pollingCommandListView.getItems());
        pane.setContent(paneRoot);
        dialog.setDialogPane(pane);
        dialog.setHeaderText("Edit Polling Commands");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.setResultConverter(new Callback<ButtonType, PollingCommandList>() {
            @Override
            public PollingCommandList call(ButtonType param) {
                if (param == ButtonType.OK) {
                    System.out.println("OK was pressed, process result");
                    return controller.getResult(pollingCommandListView.getItems());
                }
                return null;
            }
        });

        Optional<PollingCommandList> result = dialog.showAndWait();

        if (result.isPresent()) {
            pollingCommandList = result.get();
            pollingCommandList.storeListToPrefs();

        } else {
            System.out.println("result not presetn");
        }
    }

    private void initializeMenus() {
        MenuItem editPollingCommandMenuItem = new MenuItem("Edit Polling Commands");
        editPollingCommandMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    editPollingCommands();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        ContextMenu contextMenu = new ContextMenu(editPollingCommandMenuItem);
//        pollingCommandListView.setContextMenu(contextMenu);
    }


    public void setMainController(MainController mainController) {
        System.out.println("setting maincontroller to centercontroller");
        this.mainController = mainController;
    }
}

