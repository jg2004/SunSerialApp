package models;

import com.fazecast.jSerialComm.SerialPort;
import enums.ControllerType;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class Device {


    private SerialPort comPort;
    protected List<String> commandList;
    private Settings settings;
    protected List<SimpleStringProperty> responseList; //bind to response label
    protected List<SimpleStringProperty> labelList; //bind to command sent label
    private int currentPollingCommandIndex = 0;


    public Device(SerialPort comPort, Settings settings) {

        this.comPort = comPort;
        this.settings = settings;
        if (comPort != null) {
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING |
                    SerialPort.TIMEOUT_READ_BLOCKING, 1000, 100);
            comPort.openPort();
        } else {
            System.out.println("com port is NULL");
        }

        commandList = new ArrayList<>();
        responseList = new ArrayList<>();
        labelList = new ArrayList<>();
    }

    public static Device createDevice(Settings settings, SerialPort comPort) {

        ControllerType controllerType = ControllerType.valueOf(settings.getControllerType());

        switch (controllerType) {

            case EC1x:

                return new EC1xDevice(comPort, settings);

            case EC127:

                return null;

            default:
                return null;
        }
    }

    public void pollNextCommand() {

        byte[] bytesToSend = commandList.get(currentPollingCommandIndex).getBytes();
        comPort.writeBytes(bytesToSend, bytesToSend.length);
        byte[] responseByteArray = new byte[50];
        int numOfBytesReceived = comPort.readBytes(responseByteArray, responseByteArray.length);
        String response = new String(responseByteArray).trim();
        System.out.println("response length: " + numOfBytesReceived + ", response: " + response + " , command sent: " + new String(bytesToSend));
        responseList.get(currentPollingCommandIndex).set(format(response));

    }

    //incremented by CenterController
    public void incCommandCounter() {
        currentPollingCommandIndex = currentPollingCommandIndex >= (commandList.size() - 1) ? 0 : currentPollingCommandIndex + 1;
    }

    private String format(String response) {

        //remove the WAIT= and SET= from response with no set point and no wait time
        String[] formattedResponseArray;

        if (response.contains("WAIT=") || response.contains("WAIT =")) {
            //WAIT=FOREVER

            formattedResponseArray = response.split("=");
            return formattedResponseArray[1].trim();


        } else if (response.contains("SET=") || response.contains("SET =")) {
            formattedResponseArray = response.split("=");
            return formattedResponseArray[1].trim();

        }
        return response;
    }

    public List<SimpleStringProperty> getResponseList() {
        return responseList;
    }

    public List<SimpleStringProperty> getLabelList() {
        return labelList;
    }

    public int getCurrentPollingCommandIndex() {
        return currentPollingCommandIndex;
    }
}
