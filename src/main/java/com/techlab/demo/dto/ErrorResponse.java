package com.techlab.demo.dto;

public record ErrorResponse(
        int status,
        String error,
        String message
) {
}
