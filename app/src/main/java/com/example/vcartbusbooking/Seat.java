package com.example.vcartbusbooking;

public class Seat {
    private String name;
    private boolean available;
    private String fare;
    private int row;
    private int column;
    private int zIndex;
    private boolean ladiesSeat;
    private boolean malesSeat;
    private int length;
    private int width;
    private boolean selected;
    private boolean isDriverSeat; // New field to identify driver seat

    public Seat(String name, boolean available, String fare, int row, int column, int zIndex,
                boolean ladiesSeat, boolean malesSeat, int length, int width) {
        this.name = name;
        this.available = available;
        this.fare = fare;
        this.row = row;
        this.column = column;
        this.zIndex = zIndex;
        this.ladiesSeat = ladiesSeat;
        this.malesSeat = malesSeat;
        this.length = length;
        this.width = width;
        this.selected = false;
        this.isDriverSeat = false; // Default to false
    }

    // Add getter and setter for isDriverSeat
    public boolean isDriverSeat() {
        return isDriverSeat;
    }

    public void setDriverSeat(boolean driverSeat) {
        isDriverSeat = driverSeat;
    }

    // Existing getters and setters...
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    public String getFare() { return fare; }
    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getZIndex() { return zIndex; }
    public boolean isLadiesSeat() { return ladiesSeat; }
    public boolean isMalesSeat() { return malesSeat; }
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}