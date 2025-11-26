package com.techlab.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
        @Size(max = 150)
        @NotBlank
        String name,

        @NotNull
        Double price,

        @NotNull
        Integer stock
) {
}
