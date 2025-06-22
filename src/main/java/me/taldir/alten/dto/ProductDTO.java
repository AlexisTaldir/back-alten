package me.taldir.alten.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import me.taldir.alten.model.InventoryStatus;
import me.taldir.alten.model.Product;

public class ProductDTO {
    //-------------------------
    // --- Object Variables ---
    //-------------------------

    @Size(min = 3, max = 20, message = "Code must be between 3 and 20 characters")
    private String code;

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    private String image;

    @Size(min = 1, max = 50, message = "Category must be between 1 and 50 characters")
    private String category;

    @PositiveOrZero(message = "Price must be zero or positive")
    private Double price;
    
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer quantity;

    @Size(min = 1, max = 50, message = "Internal reference must be between 1 and 50 characters")
    private String internalReference;

    @PositiveOrZero(message = "Shell ID must be positive")
    private Integer shellId;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @PositiveOrZero(message = "Rating must be zero or positive")
    private Integer rating;

    //------------------------------
    // --- Default constructor ---
    //------------------------------
    public ProductDTO() {}

    
    //--------------------------------
    // --- Overloaded constructor ---
    //--------------------------------
    public ProductDTO(String code, String name, String description, String image, String category,
                            Double price, Integer quantity, String internalReference, Integer shellId,
                            InventoryStatus inventoryStatus, Integer rating) {
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

    //-------------------------------
    //      --- Function ---
    //-------------------------------

    public Product toProduct() {
        Product product = new Product();
        product.setCode(this.code);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setImage(this.image);
        product.setCategory(this.category);
        product.setPrice(this.price);
        product.setQuantity(this.quantity);
        product.setInternalReference(this.internalReference);
        product.setShellId(this.shellId);
        product.setInventoryStatus(this.inventoryStatus);
        product.setRating(this.rating);
        return product;
    }

    //-------------------------------
    // --- Getters and Setters ---
    //-------------------------------

    public String getCode() {return code;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getImage() {return image;}

    public String getCategory() {return category;}

    public Double getPrice() {return price;}

    public Integer getQuantity() {return quantity;}

    public String getInternalReference() {return internalReference;}

    public Integer getShellId() {return shellId;}

    public InventoryStatus getInventoryStatus() {return inventoryStatus;}

    public Integer getRating() {return rating;}

    public Product deltaProduct(Product product) {
        if (this.code != null && this.code != product.getCode()) {product.setCode(this.code);}
        if (this.name != null && this.name != product.getName()) {product.setName(this.name);}
        if (this.description != null && this.description != product.getDescription()) {product.setDescription(this.description);}   
        if (this.image != null && this.image != product.getImage()) {product.setImage(this.image);}
        if (this.category != null && this.category != product.getCategory()) {product.setCategory(this.category);}
        if (this.price != null && !this.price.equals(product.getPrice())) {product.setPrice(this.price);}
        if (this.quantity != null && !this.quantity.equals(product.getQuantity())) {product.setQuantity(this.quantity);}
        if (this.internalReference != null && this.internalReference != product.getInternalReference()) {product.setInternalReference(this.internalReference);}
        if (this.shellId != null && !this.shellId.equals(product.getShellId())) {product.setShellId(this.shellId);}
        if (this.inventoryStatus != null && this.inventoryStatus != product.getInventoryStatus()) {product.setInventoryStatus(this.inventoryStatus);}
        if (this.rating != null && !this.rating.equals(product.getRating())) {product.setRating(this.rating);}
        return product;
    }
}
