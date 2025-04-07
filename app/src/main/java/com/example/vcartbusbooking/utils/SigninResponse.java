package com.example.vcartbusbooking.utils;

public class SigninResponse {
    private int statuscode;
    private boolean success;
    private String message;
    private int storeid;
    private String storecode;
    private String store;
    private String sl_from; // You can create a separate class for this if needed
    private int sl_automation;
    private int delv_automation;
    private String token;
    private int userid;
    private String username;
    private int roleid;
    private String rolename;

    // Getters and Setters

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
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

    public int getStoreid() {
        return storeid;
    }

    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getSl_from() {
        return sl_from;
    }

    public void setSl_from(String sl_from) {
        this.sl_from = sl_from;
    }

    public int getSl_automation() {
        return sl_automation;
    }

    public void setSl_automation(int sl_automation) {
        this.sl_automation = sl_automation;
    }

    public int getDelv_automation() {
        return delv_automation;
    }

    public void setDelv_automation(int delv_automation) {
        this.delv_automation = delv_automation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
