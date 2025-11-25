package com.techlab.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techlab.demo.exception.InsufficientStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Double price;
    private Integer stock;

    protected Product() {
        // Constructor vacío requerido por JPA
    }

    public Product(UUID id) {
        this.id = id;
    }

    public Product(String name, Double price, Integer stock) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        this.name = name.trim();
        this.price = price;
        this.stock = stock;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.name = name.trim();
    }

    public void setPrice(Double price) {
        if (price <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        this.price = price;
    }

    public void setStock(Integer stock) {
        if (stock < 0)
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
    }

    public void reduceStock(int quantity) throws InsufficientStockException {
        if (quantity > stock) {
            throw new InsufficientStockException(
                    String.format("Stock insuficiente para '%s'. Disponible: %d, solicitado: %d",
                            name, stock, quantity)
            );
        }
        this.stock -= quantity;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Producto: %s | Precio: $%.2f | Stock: %d",
                id, name, price, stock);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return (name == null || name.trim().isEmpty()) ||
                price == null ||
                stock == null;
    }
}
