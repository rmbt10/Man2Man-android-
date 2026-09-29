package com.example.man2manwebapp.model;

public class Product {
    private String id;
    private String name;
    private double price;
    private int imageResId;  // ← Changed to int (for R.drawable.xxx)
    private String club;
    private String season;
    private String condition;

    public Product(String id, String name, double price, int imageResId, String club, String season, String condition) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.club = club;
        this.season = season;
        this.condition = condition;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getClub() { return club; }
    public String getSeason() { return season; }
    public String getCondition() { return condition; }
}