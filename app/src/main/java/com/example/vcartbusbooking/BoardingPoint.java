package com.example.vcartbusbooking;

import java.io.Serializable;

public class BoardingPoint implements Serializable {
    private String address;
    private String bpId;
    private String bpName;
    private String city;
    private String cityId;
    private String contactNumber;
    private String landmark;
    private String location;
    private String locationId;
    private boolean prime;
    private String time;

    public BoardingPoint(String address, String bpId, String bpName, String city, String cityId,
                         String contactNumber, String landmark, String location, String locationId,
                         boolean prime, String time) {
        this.address = address;
        this.bpId = bpId;
        this.bpName = bpName;
        this.city = city;
        this.cityId = cityId;
        this.contactNumber = contactNumber;
        this.landmark = landmark;
        this.location = location;
        this.locationId = locationId;
        this.prime = prime;
        this.time = time;
    }

    public String getAddress() { return address; }
    public String getBpId() { return bpId; }
    public String getBpName() { return bpName; }
    public String getCity() { return city; }
    public String getCityId() { return cityId; }
    public String getContactNumber() { return contactNumber; }
    public String getLandmark() { return landmark; }
    public String getLocation() { return location; }
    public String getLocationId() { return locationId; }
    public boolean isPrime() { return prime; }
    public String getTime() { return time; }
}