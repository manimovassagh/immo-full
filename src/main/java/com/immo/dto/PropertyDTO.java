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

    Double area,

    String photoFileName
) {
    public static PropertyDTO createEmpty() {
        return new PropertyDTO(
            "New Property",
            "Enter property description",
            "Enter property address",
            BigDecimal.ZERO,
            1,
            1,
            0.0,
            null
        );
    }
} 