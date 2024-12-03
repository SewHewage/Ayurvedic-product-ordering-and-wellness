package com.example.ayushwellness.models;

public class Product {
    private String name;
    private String price;
    private int imageResId;

    // No-argument constructor required for Firebase
    public Product() {
    }

    public Product(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setId(String key) {
    }

    public String getId() {
        return null;
    }
}
