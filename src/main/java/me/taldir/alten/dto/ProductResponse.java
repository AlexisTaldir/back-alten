package me.taldir.alten.dto;

import me.taldir.alten.model.InventoryStatus;
import me.taldir.alten.model.Product;

public class ProductResponse {

    //-------------------------
    // --- Object Variables ---
    //-------------------------

    private int id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    private String internalReference;
    private Integer shellId;
    private InventoryStatus inventoryStatus;
    private Integer rating;
    private Long createdAt;
    private Long updatedAt;

    // ---------------------------
    // --- Default constructor ---
    // ---------------------------

    public ProductResponse() {}

    // ------------------------------
    // --- Overloaded constructor ---
    // ------------------------------

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.internalReference = product.getInternalReference();
        this.shellId = product.getShellId();
        this.inventoryStatus = product.getInventoryStatus();
        this.rating = product.getRating();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    // ---------------------------
    // --- Getters and Setters ---
    // ---------------------------
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getInternalReference() { return internalReference; }
    public void setInternalReference(String internalReference) { this.internalReference = internalReference; }
    public Integer getShellId() { return shellId; }
    public void setShellId(Integer shellId) { this.shellId = shellId; }
    public InventoryStatus getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(InventoryStatus inventoryStatus) { this.inventoryStatus = inventoryStatus; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}
