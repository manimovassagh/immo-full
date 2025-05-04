package com.immo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PropertyDTO(
    @NotEmpty(message = "Title is required")
    String title,

    @NotEmpty(message = "Description is required")
    String description,

    @NotEmpty(message = "Address is required")
    String address,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    BigDecimal price,

    @NotNull(message = "Number of bedrooms is required")
    @Positive(message = "Number of bedrooms must be positive")
    Integer bedrooms,

    @NotNull(message = "Number of bathrooms is required")
    @Positive(message = "Number of bathrooms must be positive")
    Integer bathrooms,

    Double area
) {
    public PropertyDTO {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (bedrooms == null || bedrooms <= 0) {
            throw new IllegalArgumentException("Number of bedrooms must be positive");
        }
        if (bathrooms == null || bathrooms <= 0) {
            throw new IllegalArgumentException("Number of bathrooms must be positive");
        }
    }
} 