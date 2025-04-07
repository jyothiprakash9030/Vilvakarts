package com.example.vcartbusbooking;

public class SeatModel {
    private String seatNumber;
    private boolean isSelected;

    public SeatModel(String seatNumber, boolean isSelected) {
        this.seatNumber = seatNumber;
        this.isSelected = isSelected;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
