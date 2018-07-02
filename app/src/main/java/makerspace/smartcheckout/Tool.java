package makerspace.smartcheckout;

import android.graphics.Bitmap;

public class Tool {

    private int ID;
    private String containingBoxID;
    private String toolName;
    private Bitmap image;
    private boolean defected;

    public Tool(int id, String boxID, String name, Bitmap image, boolean defected){
        this.ID = id;
        this.containingBoxID = boxID;
        this.toolName = name;
        this.image = image;
        this.defected = defected;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContainingBoxID() {
        return containingBoxID;
    }

    public void setContainingBoxID(String containingBoxID) {
        this.containingBoxID = containingBoxID;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isDefected() {
        return defected;
    }

    public void setDefected(boolean defected) {
        this.defected = defected;
    }
}
