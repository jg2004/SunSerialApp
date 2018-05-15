package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import models.Settings;

import java.io.IOException;
import java.util.Optional;

public class MainController extends Application {


    private BorderPane mBorderPane;
    private Settings settings;

    @FXML
    private ImageView openFileImageView;

    @FXML
    private ImageView saveImageView;

    @FXML
    private ImageView startPollingImageView;

    @FXML
    private ImageView settingsImageView;
    private static final String TOOLTIP = "tooltip";


    @Override
    public void init() {


        System.out.println("init of MainController called");
        //create a settings object and restore state from preferences


    }


    public void initialize() {

        System.out.println("MainController initialize called");
//        Tooltip tooltip = new Tooltip("My Tooltip");
//        settingsImageView.getProperties().put(TOOLTIP, tooltip);
//        Tooltip.install(settingsImageView, tooltip);

        settings = new Settings(getClass());


    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        mBorderPane = FXMLLoader.load(getClass().getResource("main.fxml"));


        System.out.println("screen height: " + Screen.getPrimary().getVisualBounds().getHeight());
        System.out.println("screen width: " + Screen.getPrimary().getVisualBounds().getWidth());

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();


        primaryStage.setTitle("Sun Serial Communications App");
        primaryStage.setScene(new Scene(mBorderPane,.75*screenWidth,.75*screenHeight));
        primaryStage.show();
        inititializeCenterPane();


    }



    private void inititializeCenterPane() throws IOException {

        System.out.println("initializing centerpane");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("center.fxml"));
        Node center = loader.load();
        mBorderPane.setCenter(center);
        CenterController controller = loader.getController();
        controller.setMainController(this);

    }

    @FXML
    private void handleSettingsClicked() throws IOException {

        System.out.println("settings clicked");
        Dialog<Void> settingsDialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("settingsPane.fxml"));
        DialogPane dialogPane;
        dialogPane = fxmlLoader.load();
        SettingsPaneController controller = fxmlLoader.getController();
        controller.initializeSettingsValues(settings);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        settingsDialog.setDialogPane(dialogPane);
        settingsDialog.setResultConverter(param -> {
            if (param == ButtonType.OK) {
                controller.process(settings);
            }
            return null;
        });
        settingsDialog.showAndWait();
    }

    @Override
    public void stop() throws Exception {


        super.stop();
        System.out.println("MainController's stop method was called");
    }



    /*********************************getters and setters****************************************/
    public Settings getSettings() {
        System.out.println("getSettings was called, setting is: " + settings);

        return settings;
    }


    /********************************* end of getters and setters*********************************/
    public static void main(String[] args) {
        launch(args);
    }
}


