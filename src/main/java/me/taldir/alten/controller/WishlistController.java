package me.taldir.alten.controller;

import me.taldir.alten.model.Wishlist;
import me.taldir.alten.model.Product;
import me.taldir.alten.repository.WishlistRepository;
import me.taldir.alten.repository.ProductRepository;
import me.taldir.alten.repository.UserRepository;
import me.taldir.alten.dto.WishlistDTO;
import me.taldir.alten.security.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Wishlist", description = "Operations related to user wishlists")
@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
public class WishlistController {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    public WishlistController(WishlistRepository wishlistRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable int productId) {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(404).body("Product not found");
        }
        Wishlist wishlist = checkWishlistExists(userId);
        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable int productId) {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        
        Wishlist wishlist = wishlistRepository.findByUserId((long)userId);

        wishlist.getProducts().removeIf(p -> p.getId() == productId);
        wishlistRepository.save(wishlist);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<WishlistDTO> getWishlist() {
        Integer userId = authUtil.getCurrentUserIdFromToken();
        Wishlist wishlist = checkWishlistExists(userId);
        return ResponseEntity.ok(new WishlistDTO(wishlist));
    }

    private Wishlist checkWishlistExists(Integer userId) {
        Wishlist wishlist = wishlistRepository.findByUserId((long)userId);
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setUser(userRepository.findById(userId).orElse(null));
            wishlistRepository.save(wishlist);
        }
        return wishlist;
    }
}
