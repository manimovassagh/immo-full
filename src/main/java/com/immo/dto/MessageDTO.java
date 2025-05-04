package com.immo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MessageDTO(
    @NotEmpty(message = "Message content is required")
    String content,

    @NotNull(message = "Receiver ID is required")
    Long receiverId,

    @NotNull(message = "Property ID is required")
    Long propertyId
) {
    public MessageDTO {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        if (receiverId == null) {
            throw new IllegalArgumentException("Receiver ID cannot be null");
        }
        if (propertyId == null) {
            throw new IllegalArgumentException("Property ID cannot be null");
        }
    }
} 