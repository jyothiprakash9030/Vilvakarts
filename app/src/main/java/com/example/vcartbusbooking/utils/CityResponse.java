package com.example.vcartbusbooking.utils;

public class CityResponse {
    private int code;
    private boolean success;
    private String message;
    private CityData data;

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CityData getCityData() {
        return data;
    }

    public void setCityData(CityData data) {
        this.data = data;
    }
}

