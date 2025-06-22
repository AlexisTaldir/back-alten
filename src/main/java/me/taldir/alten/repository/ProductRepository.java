package me.taldir.alten.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.taldir.alten.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {}    