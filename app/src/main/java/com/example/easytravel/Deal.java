package com.example.easytravel;

import java.io.Serializable;

public class Deal implements Serializable {
    private String id;
    private String title;
    private String description;
    private String category;
    private String subCategory;
    private String price;         // 改为 String，方便直接显示
    private String imageUrl;
    private String supplierId;
    private String supplierName;
    private String supplier;      // 👈 新增：兼容 Firebase 里的 "supplier" 字段
    private String location;
    private double rating;
    private boolean isApproved;

    public Deal() {
    }

    // 12个参数构造方法
    public Deal(String id, String title, String description, String category,
                String subCategory, String price, String imageUrl,
                String supplierId, String supplierName, String location,
                double rating, boolean isApproved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.imageUrl = imageUrl;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplier = supplierName; // 👈 同步赋值
        this.location = location;
        this.isApproved = isApproved;
        this.rating = Math.round((4.0 + (Math.random() * 1.0)) * 10.0) / 10.0;
    }

    // 10个参数构造方法
    public Deal(String id, String title, String description, String category,
                String price, String imageUrl, String supplier, String location,
                boolean available, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.supplierName = supplier;
        this.supplier = supplier; // 👈 同步赋值
        this.location = location;
        this.isApproved = available;
        this.rating = Math.round((4.0 + (Math.random() * 1.0)) * 10.0) / 10.0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    // 👈 补上 getSupplier 和 setSupplier，解决你之前的红字报错
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
        this.supplierName = supplier;
    }

    public String getSupplierName() {
        // 👈 智能判断：优先读 supplierName，没有就读 supplier，再没有就给默认值
        if (supplierName != null && !supplierName.isEmpty()) return supplierName;
        if (supplier != null && !supplier.isEmpty()) return supplier;
        return "Local Provider";
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        this.supplier = supplierName;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}