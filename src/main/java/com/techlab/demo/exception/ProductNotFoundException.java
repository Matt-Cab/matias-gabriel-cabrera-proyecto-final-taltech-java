package com.techlab.demo.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(UUID id) {
        super(String.format("El producto con id '%s' no fue encontrado.", id));
    }
}
