package com.techlab.demo.controller;

import com.techlab.demo.model.Product;
import com.techlab.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

//    @Autowired // el autowired tambien puede ir sobre el constructor en el caso de que haya multiples constructores para indicar cual utilizar
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return this.productService.createProduct(product);
    }

//    public List<String> showProducts(@RequestParam(required = false, defaultValue = "") String productName, @RequestParam double price) {
    @GetMapping("/products")
    public List<Product> showProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double productPrice) {
        return this.productService.getProducts(productName, productPrice);
    }

//    // TODO: fix
//    @GetMapping("/products/productName")
//    public List<String> getProductsByName(@RequestParam String productName) {
//        return List.of("producto1", productName);
//    }
//
//    @GetMapping("/products/productPrice")
//    public List<String> getProductsByPrice(@RequestParam double productPrice) {
//        return List.of("producto1", String.valueOf(productPrice));
//    }

//    @PutMapping("/products/{id}")
//    public String editProduct(@PathVariable(name = "id") String productId) {
    @PutMapping("/products/{id}")
    public Product editProduct(@PathVariable UUID id, @RequestBody Product productData) {
        return this.productService.editProduct(id, productData);
    }

    //    @PutMapping("/products/{id}")
    //    public String deleteProduct(@PathVariable(name = "id") String productId) {
    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable UUID id) {
        return this.productService.deleteProduct(id);
    }
//    case 1 -> createProduct(productsList);
//    case 2 -> showProducts(productsList);
//    case 3 -> getProductsByName(productsList);
//    case 4 -> editProduct(productsList);
//    case 5 -> deleteProduct(productsList);
//    case 6 -> createOrder(productsList, ordersList);
//    case 7 -> showOrders(ordersList);
}
