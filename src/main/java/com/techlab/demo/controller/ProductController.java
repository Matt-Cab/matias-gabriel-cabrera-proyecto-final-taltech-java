package com.techlab.demo.controller;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.model.Product;
import com.techlab.demo.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RequiredArgsConstructor
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

        ProductResponse response = this.productServiceImpl.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

//    public List<String> showProducts(@RequestParam(required = false, defaultValue = "") String productName, @RequestParam double price) {
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false, defaultValue = "") String productName,
            @RequestParam(required = false) Double productPrice) {

        List<ProductResponse> response = this.productServiceImpl.getProducts(productName, productPrice);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {

        ProductResponse response = this.productServiceImpl.getProductById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductUpdateRequest productData) {

        ProductResponse response = this.productServiceImpl.updateProduct(id, productData);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id)  {

        Product response = this.productServiceImpl.deleteProduct(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
