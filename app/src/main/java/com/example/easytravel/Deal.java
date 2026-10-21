package com.example.easytravel;

import java.io.Serializable;

public class Deal implements Serializable {

    private String id;
    private String title;
    private String description;
    private String category;
    private String price;
    private String imageUrl;
    private String supplier;
    private String supplierName;
    private double rating;
    private String location;
    private boolean available;
    private String type;

    // 必须保留无参构造（Firebase 序列化需要）
    public Deal() {
    }

    // 之前用的 10 个参数的构造方法（保留以免其它页面报错）
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
        this.supplierName = supplier; // 默认让他俩一样
        this.location = location;
        this.available = available;
        this.type = type;
        this.rating = 4.5; // 默认给个评分
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
    public void setSupplier(String supplier) {
        this.supplier = supplier;
        this.supplierName = supplier; // 同步更新
    }

    // Adapter 中需要的新方法
    public String getSupplierName() { return supplierName != null ? supplierName : supplier; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    // Adapter 中需要的新方法
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}