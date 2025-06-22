package me.taldir.alten.dto;

import me.taldir.alten.model.CartItem;

public class CartItemDTO {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public CartItemDTO() {}
    public CartItemDTO(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    public CartItemDTO(CartItem item) {
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.quantity = item.getQuantity();
        this.price = item.getProduct().getPrice() != null ? item.getProduct().getPrice() : 0.0;
    }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
