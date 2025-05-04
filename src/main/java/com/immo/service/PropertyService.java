package com.immo.service;

import com.immo.dto.PropertyDTO;
import com.immo.entity.Property;

import java.util.List;

public interface PropertyService {
    Property createProperty(PropertyDTO propertyDTO, Long ownerId);
    Property findById(Long id);
    List<Property> findAll();
    List<Property> findByOwnerId(Long ownerId);
    Property updateProperty(Long id, PropertyDTO propertyDTO);
    void deleteProperty(Long id);
} 