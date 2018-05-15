package enums;

/**
 * Created by Jerry on 4/11/2018.
 */
public enum ControllerType {


    EC1x("EC1X"),
    TC01("TC01"),
    PC100("PC100"),
    PC1000("PC1000"),
    TC02("TC02"),
    EC127("EC127"),
    PC100_2("PC100-2");

    String type;

    ControllerType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
