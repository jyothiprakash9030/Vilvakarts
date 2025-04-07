package com.example.vcartbusbooking;

public class PassengerInfo {
    private String name;
    private String gender;
    private String age;
    private String seatNumber;

    private  String whatsappNumber;
    private String deck = ""; // Added deck field
    private boolean checkboxNotifications; // Change from RadioButton to boolean

    public PassengerInfo() {
        this.name = "";
        this.gender = "";
        this.age = "";
        this.seatNumber = "";
        this.checkboxNotifications = false; // Default: Not checked
        this.whatsappNumber="";
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public boolean isCheckboxNotifications() { return checkboxNotifications; } // Changed to boolean
    public void setCheckboxNotifications(boolean checkboxNotifications) {
        this.checkboxNotifications = checkboxNotifications;
    }


    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }



    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }
}
