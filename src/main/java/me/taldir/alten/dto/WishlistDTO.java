package me.taldir.alten.dto;

import me.taldir.alten.model.Wishlist;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistDTO {
    private List<WishlistProductDTO> products;

    public WishlistDTO() {}
    public WishlistDTO(List<WishlistProductDTO> products) {
        this.products = products;
    }
    public WishlistDTO(Wishlist wishlist) {
        this.products = wishlist.getProducts().stream().map(WishlistProductDTO::new).collect(Collectors.toList());
    }
    public List<WishlistProductDTO> getProducts() { return products; }
    public void setProducts(List<WishlistProductDTO> products) { this.products = products; }
}
