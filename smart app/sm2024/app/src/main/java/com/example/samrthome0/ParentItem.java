package com.example.samrthome0;

public class ParentItem {

    String orderId;
    int imageId;

    public ParentItem(String orderId, int imageId) {
        this.orderId = orderId;
        this.imageId = imageId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getOrderId() {
        return orderId;
    }
}
