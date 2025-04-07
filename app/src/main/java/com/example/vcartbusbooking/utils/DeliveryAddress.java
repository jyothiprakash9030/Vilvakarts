package com.example.vcartbusbooking.utils;

public class DeliveryAddress {

    private int id;
    private int storeid;
    private int cust_id;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private String pincode;
    private String doorno_streetaddress;
    private String location_town_district;
    private String emailaddress;
    private String mobile_prefix;
    private String phonenumber;
    private String primary_addr;
    private int recentlyused;
    private String created_at;
    private String updated_at;
    private Object migrated; // use appropriate type based on expected data
    private boolean selected_address;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDoorno_streetaddress() {
        return doorno_streetaddress;
    }

    public void setDoorno_streetaddress(String doorno_streetaddress) {
        this.doorno_streetaddress = doorno_streetaddress;
    }

    public String getLocation_town_district() {
        return location_town_district;
    }

    public void setLocation_town_district(String location_town_district) {
        this.location_town_district = location_town_district;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getMobile_prefix() {
        return mobile_prefix;
    }

    public void setMobile_prefix(String mobile_prefix) {
        this.mobile_prefix = mobile_prefix;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPrimary_addr() {
        return primary_addr;
    }

    public void setPrimary_addr(String primary_addr) {
        this.primary_addr = primary_addr;
    }

    public int getRecentlyused() {
        return recentlyused;
    }

    public void setRecentlyused(int recentlyused) {
        this.recentlyused = recentlyused;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getMigrated() {
        return migrated;
    }

    public void setMigrated(Object migrated) {
        this.migrated = migrated;
    }

    public boolean isSelected_address() {
        return selected_address;
    }

    public void setSelected_address(boolean selected_address) {
        this.selected_address = selected_address;
    }
}
