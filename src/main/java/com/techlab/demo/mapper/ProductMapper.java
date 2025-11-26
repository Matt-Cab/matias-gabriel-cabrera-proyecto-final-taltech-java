package com.techlab.demo.mapper;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product createToProduct(ProductCreateRequest request) {
        Product product = new Product();

        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return  product;
    }

    public Product updateToProduct(Product product, ProductUpdateRequest request) {

        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return  product;
    }

    public ProductResponse toResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
