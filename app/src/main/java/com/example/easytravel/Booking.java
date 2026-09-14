package com.example.easytravel;


public class Booking {
    private String id;
    private String dealId;
    private String dealTitle;
    private String customerId;
    private String customerName;
    private String supplierId;
    private String date;          // "20/09/2026"
    private int numberOfPeople;
    private double totalPrice;
    private String status;        // "PENDING", "CONFIRMED", "CANCELLED", "COMPLETED"
    private long timestamp;

    public Booking() {
    }

    public Booking(String id, String dealId, String dealTitle, String customerId,
                   String customerName, String supplierId, String date,
                   int numberOfPeople, double totalPrice, String status, long timestamp) {
        this.id = id;
        this.dealId = dealId;
        this.dealTitle = dealTitle;
        this.customerId = customerId;
        this.customerName = customerName;
        this.supplierId = supplierId;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDealId() { return dealId; }
    public void setDealId(String dealId) { this.dealId = dealId; }

    public String getDealTitle() { return dealTitle; }
    public void setDealTitle(String dealTitle) { this.dealTitle = dealTitle; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}