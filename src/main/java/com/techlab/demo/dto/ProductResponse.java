package com.techlab.demo.dto;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        Double price,
        Integer stock
) {
}
