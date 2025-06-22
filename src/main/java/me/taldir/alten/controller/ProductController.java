package me.taldir.alten.controller;

import me.taldir.alten.dto.ProductDTO;
import me.taldir.alten.dto.ErrorResponse;
import me.taldir.alten.dto.ProductResponse;
import me.taldir.alten.model.Product;
import me.taldir.alten.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/products")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Product Management", description = "Operations related to product management")
public class ProductController {
    
    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "Create a new product", description = "Saves a new product and returns the saved entity",
    responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productDTO.toProduct();
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(savedProduct));
    }

    @Operation(summary = "Get all products", description = "Retrieves a list of all products in the database",
    responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> dtos = productRepository.findAll().stream()
            .map(ProductResponse::new)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID",
    responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Product not found"));
        }
        return ResponseEntity.ok(new ProductResponse(productOpt.get()));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update product partially", description = "Updates specific fields of a product by its ID",
    responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProductPartial(@PathVariable Integer id, @RequestBody ProductDTO updates) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Product not found"));
        }
        Product updatedProduct = updates.deltaProduct(productOpt.get());
        Product saved = productRepository.save(updatedProduct);
        return ResponseEntity.ok(new ProductResponse(saved));
    }

    @Operation(summary = "Delete product by ID", description = "Deletes a product by its ID", 
    responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Product not found"));
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
