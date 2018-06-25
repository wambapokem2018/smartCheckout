package makerspace.smartcheckout;

public class Box {


    private String BoxName;
    private String BoxImage;


    public String getBoxName() {
        return BoxName;
    }

    public void setBoxName(String boxName) {
        BoxName = boxName;
    }

    public String getBoxImage() {
        return BoxImage;
    }

    public void setBoxImage(String boxImage) {
        BoxImage = boxImage;
    }

    public Box(String Name, String Image){
        this.BoxImage = Image;
        this.BoxName = Name;

    }
}
