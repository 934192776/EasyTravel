package com.example.easytravel;

public class Deal {
    private String id;
    private String title;
    private String description;
    private String category;      // "City", "Adventure", "Food", "Nature"
    private String subCategory;   // "Seafood", "Classic Kiwi", "Asian Fusion", "Dessert & Cafe"
    private double price;
    private String imageUrl;
    private String supplierId;
    private String supplierName;
    private String location;
    private double rating;
    private boolean isApproved;

    public Deal() {
    }

    public Deal(String id, String title, String description, String category,
                String subCategory, double price, String imageUrl,
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
        this.location = location;
        this.rating = rating;
        this.isApproved = isApproved;
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

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
}