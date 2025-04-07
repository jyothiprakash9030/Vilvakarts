package com.example.vcartbusbooking;

public class Goods {
    private int id;
    private int categoryId;
    private String name;
    private String description;
    private String imageUrl;
    private String mrp;
    private String sp;
    private int stock;
    private String weight;
    public Goods(int id, int categoryId, String name, String description, String imageUrl, String mrp, String sp, int stock, String weight) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.mrp = mrp;
        this.sp = sp;
        this.stock = stock;
        this.weight = weight;
    }
    public int getId() { return id; }
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getMrp() { return mrp; }
    public String getSp() { return sp; }
    public int getStock() { return stock; }
    public String getWeight() { return weight; }
}
