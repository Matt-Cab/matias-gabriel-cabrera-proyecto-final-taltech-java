package com.techlab.demo.service;

import com.techlab.demo.model.Product;
//import com.techlab.demo.repository.ProductMemRepository;
import com.techlab.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    // private ProductMemRepository productMemRepository;
    private ProductRepository repository;

    public ProductService(ProductRepository productMemRepository) {
        this.repository = productMemRepository;
    }

    public Product createProduct(Product product) {
        Product newProduct = null;

        try {
            newProduct = this.repository.save(product);
            System.out.println("Producto agregado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }

        return newProduct;
    }

//    public List<Product> getProducts() {
//        List<Product> productsList = null;
//
//        try {
//            productsList = this.repository.findAll();
//            System.out.println("Listando productos.");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Error al intentar obtener los productos: " + e.getMessage());
//        }
//
//        return productsList;
//    }

    public List<Product> getProducts(String name, Double price) {
        if (name != null && !name.isEmpty() && price != null && price > 0) {
            System.out.printf("Listando productos que contengan \"%s\" y con precio menor o igual a %f\n", name, price);
            return this.repository.findByNameContainingAndPriceLessThanEqual(name, price);
        }

        if (name != null && !name.isEmpty()) {
            System.out.printf("Listando productos que contengan \"%s\"\n", name);
            return this.repository.findByNameContaining(name);
        }

        if (price != null && price > 0) {
            System.out.printf("Listando productos con precio menor o igual a %f\n", price);
            return this.repository.findByPriceLessThanEqual(price);
        }

        return this.repository.findAll();
    }

    public Product editProduct(UUID id, Product productData) {
//        Optional<Product> productFound = this.repository.findById(id);
//        Product productFound = this.repository.findById(id).orElse(new Product(UUID.randomUUID()));
        Product productFound = this.repository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (productFound.isEmpty()) {
            System.out.println("Producto no encontrado.");
            return null;
        }
//        if (productFound == null) {
//            System.out.println("Producto no encontrado.");
//            return null;
//        }

        String newName = productData.getName();

        Double newPrice = productData.getPrice();

        Integer newStock = productData.getStock();

        try {
            if (newName != null && !newName.isEmpty()) productFound.setName(newName);
            if (newPrice != null && newPrice > 0) productFound.setPrice(newPrice);
            if (newStock != null && newStock >= 0) productFound.setStock(newStock);
            this.repository.save(productFound);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }

        return productFound;
    }

    public Product deleteProduct(UUID id) {
        Optional<Product> productFound = this.repository.findById(id);

        if (productFound.isEmpty()) {
            System.out.println("No se pudo eliminar el producto porque no fue encontrado.");
            return null;
        }

        Product productToDelete = productFound.get();

        this.repository.delete(productToDelete);

        // tambien se puede utilizar el siguiente codigo pero hay que manejar la exception (OptimisticLockingFailureException)
//        this.repository.deleteById(id);

        System.out.println("Se eliminó de con exito el producto con id: " + productToDelete.getId());
        return productToDelete;
    }
}
