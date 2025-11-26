package com.techlab.demo.service;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    List<ProductResponse> getProducts(String name, Double price);

    ProductResponse getProductById(UUID id);

    ProductResponse updateProduct(UUID id, ProductUpdateRequest productData);

    Product deleteProduct(UUID id);
}
