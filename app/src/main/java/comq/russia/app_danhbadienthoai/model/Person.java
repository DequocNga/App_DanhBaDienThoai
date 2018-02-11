package comq.russia.app_danhbadienthoai.model;

/**
 * Created by VLADIMIR PUTIN on 2/9/2018.
 */

public class Person {
    private String name;
    private String telNumber;
    private boolean isFemale;

    public Person(String name, String telNumber, boolean isFemale) {
        this.name = name;
        this.telNumber = telNumber;
        this.isFemale = isFemale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }
}
