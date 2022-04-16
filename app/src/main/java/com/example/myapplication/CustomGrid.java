package com.example.myapplication;

public class CustomGrid {
    private int image;
    private String slotName;

    public CustomGrid(int image, String slotName) {
        this.image = image;
        this.slotName = slotName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }
}
