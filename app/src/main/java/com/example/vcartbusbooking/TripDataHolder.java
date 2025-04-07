package com.example.vcartbusbooking;

import java.util.ArrayList;
import java.util.List;

public class TripDataHolder {
    private static TripDataHolder instance;
    private List<SelectedSeat> selectedSeats;
    private double totalAmount;
    private String tripId;
    private String busType;
    private String from;
    private String to;
    private String date;
    private int passengercount;
    private int fromCityId;
    private int toCityId;
    private int bpId;
    private int dpId;
     private double totalfare;
    private double fare;
    private double serviceTax;
    private String travels;
    private List<Boolean> seatAvailability = new ArrayList<>();
    private List<Boolean> ladiesSeats = new ArrayList<>();
    private List<Boolean> malesSeats = new ArrayList<>();

    private TripDataHolder() {
        selectedSeats = new ArrayList<>();
    }

    public static TripDataHolder getInstance() {
        if (instance == null) {
            instance = new TripDataHolder();
        }
        return instance;
    }

    public void setSelectedSeats(List<SelectedSeat> selectedSeats) {
        this.selectedSeats = selectedSeats != null ? new ArrayList<>(selectedSeats) : new ArrayList<>();
    }

    public List<SelectedSeat> getSelectedSeats() {
        return selectedSeats != null ? new ArrayList<>(selectedSeats) : new ArrayList<>();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusType() {
        return busType;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getPassengercount() {
        return passengercount;
    }

    public void setPassengercount(int passengercount) {
        this.passengercount = passengercount;
    }

    public int getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(int fromCityId) {
        this.fromCityId = fromCityId;
    }

    public int getToCityId() {
        return toCityId;
    }

    public void setToCityId(int toCityId) {
        this.toCityId = toCityId;
    }

    public int getBpId() {
        return bpId;
    }

    public void setBpId(int bpId) {
        this.bpId = bpId;
    }



    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getTravels() {
        return travels;
    }

    public void setTravels(String travels) {
        this.travels = travels;
    }




    public List<Boolean> getSeatAvailability() { return seatAvailability; }
    public void setSeatAvailability(List<Boolean> availability) { this.seatAvailability = availability; }
    public List<Boolean> getLadiesSeats() { return ladiesSeats; }
    public void setLadiesSeats(List<Boolean> ladies) { this.ladiesSeats = ladies; }
    public List<Boolean> getMalesSeats() { return malesSeats; }
    public void setMalesSeats(List<Boolean> males) { this.malesSeats = males; }


    public int getDpId() {
        return dpId;
    }

    public void setDpId(int dpId) {
        this.dpId = dpId;
    }

    public double getTotalfare() {
        return totalfare;
    }

    public void setTotalfare(double totalfare) {
        this.totalfare = totalfare;
    }
}