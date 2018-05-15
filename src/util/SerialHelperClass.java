package util;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_SEMI_BLOCKING;
import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING;

/**
 * Helper class with various utility methods.
 */
public class SerialHelperClass {

    /**
     * Returns a list of com ports (real and virtual) that are useable. This is required because bluetooth often
     * creates several ports that are not used.
     *
     * @param serialPorts
     * @return list of com ports that can be used for serial communication
     */
    public static void populateSerialPortList(ObservableList<SerialPort> serialPorts) {

        SerialPort[] serialPortArray;
        serialPortArray = SerialPort.getCommPorts();

        for (SerialPort port : serialPortArray) {

            if (port.openPort()) {

                //set up to block until read/write succesful or time out after 100ms
                port.setComPortTimeouts(TIMEOUT_READ_SEMI_BLOCKING | TIMEOUT_WRITE_SEMI_BLOCKING, 100, 100);
                //write a test byte to determine if port can be written to as bluetooth creates virtual ports
                //that are not useable.
                String dataStringToSend = "T\n";
                int bytesSent = port.writeBytes(dataStringToSend.getBytes(), dataStringToSend.length());
                if (bytesSent > 0) {
                    serialPorts.add(port); //port is good to go, add to list and close!
                    port.closePort();
                    System.out.println("Useable port found: " + port.getSystemPortName());
                }
            }
        }
    }

    /**
     * Displays all com ports and lists them to console
     */

    public static void displayComPorts() {

        SerialPort[] seralPorts;
        seralPorts = SerialPort.getCommPorts();
        System.out.println("Com ports found:");
        for (SerialPort port : seralPorts) {
            System.out.println(port.getSystemPortName() + ", Data bits: " + port.getNumDataBits() + ", BAUD rate: " +
                    +port.getBaudRate() + ", STOP bits: " + port.getNumStopBits());
        }
    }
}
