package com.techlab.demo.service;

import com.techlab.demo.dto.ProductCreateRequest;
import com.techlab.demo.dto.ProductResponse;
import com.techlab.demo.dto.ProductUpdateRequest;
import com.techlab.demo.exception.ProductNotFoundException;
import com.techlab.demo.mapper.ProductMapper;
import com.techlab.demo.model.Product;
import com.techlab.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        try {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return this.productMapper.toResponse(product);

        } catch (ProductNotFoundException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }

        return null;
    }

    public ProductResponse updateProduct(UUID id, ProductUpdateRequest productData) {
        try {
            Product productFound = this.productRepository.findById(id)
                            .orElseThrow(() -> new ProductNotFoundException(id));

            Product productUpdated = this.productMapper.updateToProduct(productFound, productData);

//            String newName = productData.name();
//            Double newPrice = productData.price();
//            Integer newStock = productData.stock();
//
//            if (newName != null && !newName.isEmpty()) productFound.setName(newName);
//            if (newPrice != null && newPrice > 0) productFound.setPrice(newPrice);
//            if (newStock != null && newStock >= 0) productFound.setStock(newStock);

            try {
                Product productSaved = this.productRepository.save(productUpdated);
                return this.productMapper.toResponse(productSaved);
            } catch (RuntimeException e) {
                System.out.println("Error al actualizar producto: " + e.getMessage());
            }
        } catch (ProductNotFoundException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }

        return null;
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
