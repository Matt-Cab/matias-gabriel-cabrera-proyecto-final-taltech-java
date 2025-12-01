package com.techlab.demo.service;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.exception.ProductNotFoundException;
import com.techlab.demo.mapper.ProductMapper;
import com.techlab.demo.model.Product;
import com.techlab.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RequiredArgsConstructor // me permite evitar tener que crear el constructor de manera manual
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = this.productMapper.createToProduct(request);

        Product productSaved = this.productRepository.save(product);

        return this.productMapper.toResponse(productSaved);
    }

    @Override
    public List<ProductResponse> getProducts(String name, Double price) {
        List<Product> productsList;

        if (name != null && !name.isEmpty() && price != null && price > 0) {
            System.out.printf("Listando productos que contengan \"%s\" y con precio menor o igual a %f\n", name, price);
            productsList = this.productRepository.findByNameContainingAndPriceLessThanEqual(name, price);
        }

        else if (name != null && !name.isEmpty()) {
            System.out.printf("Listando productos que contengan \"%s\"\n", name);
            productsList = this.productRepository.findByNameContaining(name);
        }

        else if (price != null && price > 0) {
            System.out.printf("Listando productos con precio menor o igual a %f\n", price);
            productsList = this.productRepository.findByPriceLessThanEqual(price);
        }

        else {
            productsList = this.productRepository.findAll();
        }

        return productsList.stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(UUID id) {

        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return this.productMapper.toResponse(product);

    }

    public ProductResponse updateProduct(UUID id, ProductUpdateRequest productData) {

            Product productFound = this.productRepository.findById(id)
                            .orElseThrow(() -> new ProductNotFoundException(id));

            Product productUpdated = this.productMapper.updateToProduct(productFound, productData);

            Product productSaved = this.productRepository.save(productUpdated);

            return this.productMapper.toResponse(productSaved);
    }

    @Override
    public Product deleteProduct(UUID id) {
        Optional<Product> productFound = this.productRepository.findById(id);

        if (productFound.isEmpty()) {
            System.out.println("No se pudo eliminar el producto porque no fue encontrado.");
            return null;
        }

        Product productToDelete = productFound.get();

        this.productRepository.delete(productToDelete);

        System.out.println("Se eliminó de con exito el producto con id: " + productToDelete.getId());
        return productToDelete;
    }
}
