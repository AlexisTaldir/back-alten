package me.taldir.alten.dto;

public class WishlistProductDTO {
    private int productId;
    private String productName;
    private double price;

    public WishlistProductDTO() {}
    public WishlistProductDTO(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
    public WishlistProductDTO(me.taldir.alten.model.Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice() != null ? product.getPrice() : 0.0;
    }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
