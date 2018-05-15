package enums;

/**
 * Created by Jerry on 4/12/2018.
 */
public enum ErrorMessages {


    FXMLLoaderErrorMessage("Could not load fxml file");


    String message;

    ErrorMessages(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }

    public void appendToDefErrorMessage(String appendedMessage){


        message = new StringBuilder().append(message).append(": ").append(appendedMessage).toString();
    }
}
