package enums;

/**
 * Created by Jerry on 4/11/2018.
 */
public enum BaudRate {


    BAUD_RATE_38400("38400"),
    BAUD_RATE_9600("9600"),
    BAUD_RATE_4800("4800"),
    BAUD_RATE_2400("2400"),;

    String baudrate;
    BaudRate(String s) {

        baudrate = s;
    }

    public String getBaudrate() {
        return baudrate;
    }
}
