package com.example.vcartbusbooking.utils;

public class UpdateOrderResponse {
    private int statuscode;
    private boolean success;
    private String message;

    // Constructor
    public UpdateOrderResponse(int statuscode, boolean success, String message) {
        this.statuscode = statuscode;
        this.success = success;
        this.message = message;
    }

    // Getters
    public int getStatuscode() {
        return statuscode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
