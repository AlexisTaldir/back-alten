package me.taldir.alten.dto;

import me.taldir.alten.model.Cart;
import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
    private List<CartItemDTO> items;
    private double total;

    public CartDTO() {}
    public CartDTO(List<CartItemDTO> items, double total) {
        this.items = items;
        this.total = total;
    }
    public CartDTO(Cart cart) {
        this.items = cart.getItems().stream().map(CartItemDTO::new).collect(Collectors.toList());
        this.total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
