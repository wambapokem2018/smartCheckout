package makerspace.smartcheckout;

public class User {

    private int ID;
    private String name;
    private String surname;
    private String address;
    private String email;
    private int warnings;

    public User(int id, String name, String surname, String address, String email, int warnings){
        this.ID = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.warnings = warnings;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }
}
