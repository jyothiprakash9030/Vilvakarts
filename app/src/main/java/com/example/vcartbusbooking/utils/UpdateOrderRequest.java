package com.example.vcartbusbooking.utils;

public class UpdateOrderRequest {

    private String order_id;
    private int courier;
    private String tracking_no;

    // Constructor
    public UpdateOrderRequest(String order_id, int courier, String tracking_no) {
        this.order_id = order_id;
        this.courier = courier;
        this.tracking_no = tracking_no;
    }

    // Getters and Setters (if necessary)
    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public int getCourier() {
        return courier;
    }

    public void setCourier(int courier) {
        this.courier = courier;
    }

    public String getTrackingNo() {
        return tracking_no;
    }

    public void setTrackingNo(String tracking_no) {
        this.tracking_no = tracking_no;
    }

}
