package com.example.vcartbusbooking;

public class SearchProduct {
    private String productName;
    private int productImage;
    private String delivery_time;
    private String product_description;
    private int product_price;

    public SearchProduct(String productName, int productImage, String delivery_time, String product_description, int product_price) {
        this.productName = productName;
        this.productImage = productImage;
        this.delivery_time = delivery_time;
        this.product_description = product_description;
        this.product_price = product_price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
}
