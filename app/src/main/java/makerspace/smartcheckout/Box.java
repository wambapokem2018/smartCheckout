package makerspace.smartcheckout;

import android.graphics.Bitmap;

public class Box {


    private String BoxName;
    private Bitmap BoxImage;
    private String BoxID;


    public String getBoxName() {
        return BoxName;
    }

    public void setBoxName(String boxName) {
        BoxName = boxName;
    }

    public Bitmap getBoxImage() {
        return BoxImage;
    }

    public void setBoxImage(Bitmap boxImage) {
        BoxImage = boxImage;
    }

    public Box(String ID, String Name, Bitmap Image){
        this.BoxID = ID;
        this.BoxImage = Image;
        this.BoxName = Name;
    }

    public String getBoxID() {
        return BoxID;
    }

    public void setBoxID(String boxID) {
        BoxID = boxID;
    }
}
