package com.example.vcartbusbooking;

public class Trip {
    private String id;
    private String travels;
    private String busType;
    private String busRoutes;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private double lowestBaseFare; // Changed to double, removed baseFare
    private int availableSeats;
    private boolean isSleeper;
    private boolean isAC;
    private int departureMinutes;
    private int arrivalMinutes;

    public Trip(String id, String travels, String busType, String busRoutes, String departureTime,
                String arrivalTime, String duration, String lowestBaseFare, int availableSeats) {
        this.id = id;
        this.travels = travels;
        this.busType = busType;
        this.busRoutes = busRoutes;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        try {
            this.lowestBaseFare = lowestBaseFare.equals("N/A") ? Double.MAX_VALUE : Double.parseDouble(lowestBaseFare);
        } catch (NumberFormatException e) {
            this.lowestBaseFare = Double.MAX_VALUE; // Default for invalid fares
        }
        this.availableSeats = availableSeats;
        this.isAC = false; // Default value
    }

    public String getId() { return id; }
    public String getTravels() { return travels; }
    public String getBusType() { return busType; }
    public String getBusRoutes() { return busRoutes; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public String getDuration() { return duration; }
    public double getLowestBaseFare() { return lowestBaseFare; } // Updated getter
    public int getAvailableSeats() { return availableSeats; }
    public boolean isAC() { return isAC; }
    public boolean isSleeper() { return isSleeper; }
    public int getDepartureMinutes() { return departureMinutes; }
    public int getArrivalMinutes() { return arrivalMinutes; }

    public void setAC(boolean isAC) { this.isAC = isAC; }
    public void setSleeper(boolean isSleeper) { this.isSleeper = isSleeper; }
    public void setDepartureMinutes(int minutes) { this.departureMinutes = minutes; }
    public void setArrivalMinutes(int minutes) { this.arrivalMinutes = minutes; }
}