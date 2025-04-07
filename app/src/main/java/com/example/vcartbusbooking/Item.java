package com.example.vcartbusbooking;
public class Item {
    private String busName;
    private int price;
    private String startTime;
    private String endTime;

    public Item(String busName, int price, String startTime, String endTime) {
        this.busName = busName;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public String getBusName() {
        return busName;
    }

    public int getPrice() {
        return price;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
