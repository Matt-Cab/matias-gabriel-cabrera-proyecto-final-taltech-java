package com.techlab.demo.repository;

import com.techlab.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByNameContaining(String name);

    List<Product> findByPriceLessThanEqual(Double price);

    List<Product> findByNameContainingAndPriceLessThanEqual(String name, Double price);
}
