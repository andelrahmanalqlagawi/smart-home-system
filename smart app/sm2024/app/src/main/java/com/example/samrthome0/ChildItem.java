package com.example.samrthome0;

public class ChildItem {
    String itemName;
    int imageID;
    //String identifier;

    public ChildItem(String itemName, int imageID) {
        this.itemName = itemName;
        this.imageID = imageID;
        //this.identifier = identifier;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

//    public void setIdentifier(String identifier) {
//        this.identifier = identifier;
//    }

    public String getItemName() {
        return itemName;
    }

    public int getImageID() {
        return imageID;
    }

//    public String getIdentifier() {
//        return identifier;
//    }
}