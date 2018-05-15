package services;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import util.SerialHelperClass;

/**
 * populates the serialPort list that was passed in
 */
public class GetComPortService extends Service<Void> {

    private ObservableList<SerialPort> serialPorts;
    private Label progressLabel;

    public GetComPortService(Label progressLabel, ObservableList<SerialPort> serialPorts) {

        this.progressLabel = progressLabel;
        this.serialPorts = serialPorts;
    }

    @Override
    protected Task<Void> createTask() {

        System.out.println("creating and returning the task");
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                System.out.println("task called in thread: " + Thread.currentThread().getName());
                SerialHelperClass.populateSerialPortList(serialPorts);
                return null;
            }
        };

        return task;
    }
}