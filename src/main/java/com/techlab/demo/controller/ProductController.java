package com.techlab.demo.controller;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.model.Product;
import com.techlab.demo.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    // @Valid me permite validar con las anotaciones dentro del dto
    // @RequestBody me permite recibir los datos en formato json
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.productServiceImpl.createProduct(request));
    }

//    public List<String> showProducts(@RequestParam(required = false, defaultValue = "") String productName, @RequestParam double price) {
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false, defaultValue = "") String productName,
            @RequestParam(required = false) Double productPrice) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productServiceImpl.getProducts(productName, productPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productServiceImpl.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductUpdateRequest productData) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productServiceImpl.updateProduct(id, productData));
    }

    //    @PutMapping("/products/{id}")
    //    public String deleteProduct(@PathVariable(name = "id") String productId) {
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id)  {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productServiceImpl.deleteProduct(id));
    }

}
