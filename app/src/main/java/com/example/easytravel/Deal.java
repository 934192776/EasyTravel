package com.example.easytravel.model;

import java.io.Serializable;

public class Deal implements Serializable {
    private String id;
    private String title;
    private String description;
    private String category;
    private String price;
    private String imageUrl;
    private String supplier;
    private String location;
    private boolean available;
    private String type;

    // ===== 无参构造方法（Firebase 需要） =====
    public Deal() {}

    // ===== 完整构造方法 =====
    public Deal(String id, String title, String description, String category,
                String price, String imageUrl, String supplier, String location,
                boolean available, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.supplier = supplier;
        this.location = location;
        this.available = available;
        this.type = type;
    }

    // ===== Getter 和 Setter =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}