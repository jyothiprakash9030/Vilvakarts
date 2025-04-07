package com.example.vcartbusbooking;

public class Update_passenger

{

    private String name;
    private String gender;
    private String age;
    private String seatNumber;

    private  String whatsappNumber;

    public Update_passenger() {
        this.name = " ";
        this.gender = " ";
        this.age = " ";
        this.seatNumber = " ";
        this.whatsappNumber = " ";
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }
}
