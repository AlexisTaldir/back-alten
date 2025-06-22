package me.taldir.alten.controller;

import me.taldir.alten.model.*;
import me.taldir.alten.repository.*;
import me.taldir.alten.security.AuthUtil;
import me.taldir.alten.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Cart", description = "Operations related to user carts")
@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    private AuthUtil authUtil;

    public CartController(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @PatchMapping("/{productId}/{quantity}")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Set product quantity in cart",
        description = "Sets the quantity of a product in the user's cart. Returns 400 if requested quantity exceeds available stock.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Cart updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Requested quantity exceeds available stock"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
        }
    )
    public ResponseEntity<?> setProductQuantityInCart(@PathVariable int productId, @PathVariable int quantity) {
        if (quantity < 1) {
            return ResponseEntity.badRequest().body("Quantity must be at least 1");
        }
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(404).body("Product not found");
        }
        if (quantity > product.getQuantity()) {
            return ResponseEntity.badRequest().body("Requested quantity exceeds available stock");
        }
        Cart cart = checkCartExists(userId);
        CartItem item = cart.getItems().stream()
            .filter(i -> i.getProduct().getId() == productId)
            .findFirst()
            .orElse(null);
        if (item == null) {
            item = new CartItem();
            item.setProduct(product);
            item.setCart(cart);
            cart.getItems().add(item);
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Add product to cart",
        description = "Adds a product to the user's cart. If the cart does not exist, it creates a new one. Returns 400 if stock insuffisant. Si déjà présent, ne fait rien.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product added to cart successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Requested quantity exceeds available stock"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
        }
    )
    public ResponseEntity<?> addProductToCart(@PathVariable int productId) {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(404).body("Product not found");
        }
        Cart cart = checkCartExists(userId);
        boolean alreadyInCart = cart.getItems().stream()
            .anyMatch(item -> item.getProduct().getId() == productId);
        if (alreadyInCart) {
            return ResponseEntity.noContent().build(); // Ne fait rien si déjà présent
        }
        if (product.getQuantity() < 1) {
            return ResponseEntity.badRequest().body("Requested quantity exceeds available stock");
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(1);
        newItem.setCart(cart);
        cart.getItems().add(newItem);
        cartItemRepository.save(newItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Remove product from cart",
        description = "Removes a product from the user's cart, peu importe la quantité.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product removed from cart successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found in cart")
        }
    )
    public ResponseEntity<?> removeProductFromCart(@PathVariable int productId) {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Cart cart = checkCartExists(userId);
        CartItem itemToRemove = cart.getItems().stream()
            .filter(item -> item.getProduct().getId() == productId)
            .findFirst()
            .orElse(null);
        if (itemToRemove == null) {
            return ResponseEntity.status(404).body("Product not found in cart");
        }
        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Get user's cart",
        description = "Retrieves the cart for a specific user. If the cart does not exist, it creates a new one.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    public ResponseEntity<CartDTO> getCart() {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Cart cart = checkCartExists(userId);
        return ResponseEntity.ok(new CartDTO(cart));
    }

    private Cart checkCartExists(Integer userId) {
        Cart cart = cartRepository.findByUserId((long)userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(userRepository.findById(userId).orElse(null));
            cartRepository.save(cart);
        }
        return cart;
    }
}
