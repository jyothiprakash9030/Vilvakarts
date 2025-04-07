package com.example.vcartbusbooking;

public class Brand {
    private int imageResId;
    private String name;

    public Brand(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }
}

