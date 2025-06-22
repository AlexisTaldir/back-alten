package me.taldir.alten.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Product {

    //-------------------------
    // --- Object Variables ---
    //-------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    private Integer rating;

    private Long createdAt;
    private Long updatedAt;

    //----------------------------
    // --- Default constructor ---
    //----------------------------

    public Product() {}

    //-------------------------------
    // --- Overloaded constructor ---
    //-------------------------------

    public Product(String code, String name, String description, String image, String category, Double price, Integer quantity, String internalReference, Integer shellId, InventoryStatus inventoryStatus, Integer rating) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.internalReference = internalReference;
        this.shellId = shellId;
        this.inventoryStatus = inventoryStatus;
        this.rating = rating;
    }

    //-----------------------------
    // --- Getters and Setters ---
    //-----------------------------

    public int getId() { return id; }

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

    public Long getUpdatedAt() { return updatedAt; }

    //-------------------------------------
    // --- JPA Callbacks for Timestamps ---
    //-------------------------------------

    @PrePersist
    protected void onCreate() {
        createdAt = System.currentTimeMillis();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}
