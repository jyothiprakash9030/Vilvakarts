package com.example.vcartbusbooking.utils;

public class User {
    private String username;
    private String token;
    private int storeId;
    private String storeCode;
    private String storeName;
    private int roleId;
    private String roleName;

    // Constructor
    public User(String username, String token, int storeId, String storeCode, String storeName, int roleId, String roleName) {
        this.username = username;
        this.token = token;
        this.storeId = storeId;
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public User() {
        this.username = "";  // Default to empty string if null
        this.token =  "";            // Default to empty string if null
        this.storeId = 0;                               // Mandatory
        this.storeCode = ""; // Default to empty string if null
        this.storeName = ""; // Default to empty string if null
        this.roleId = 0;                                 // Mandatory
        this.roleName = "";   // Default to empty string if null
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
