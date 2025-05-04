package com.immo.service.impl;

import com.immo.dto.PropertyDTO;
import com.immo.entity.Property;
import com.immo.entity.User;
import com.immo.repository.PropertyRepository;
import com.immo.service.PropertyService;
import com.immo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Property createProperty(PropertyDTO propertyDTO, Long ownerId) {
        User owner = userService.findById(ownerId);
        
        Property property = new Property();
        property.setTitle(propertyDTO.title());
        property.setDescription(propertyDTO.description());
        property.setAddress(propertyDTO.address());
        property.setPrice(propertyDTO.price());
        property.setBedrooms(propertyDTO.bedrooms());
        property.setBathrooms(propertyDTO.bathrooms());
        property.setArea(propertyDTO.area());
        property.setOwner(owner);

        return propertyRepository.save(property);
    }

    @Override
    public Property findById(Long id) {
        return propertyRepository.findByIdWithOwner(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + id));
    }

    @Override
    public List<Property> findAll() {
        return propertyRepository.findAllWithOwner();
    }

    @Override
    public List<Property> findByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public Property updateProperty(Long id, PropertyDTO propertyDTO) {
        Property property = findById(id);
        
        property.setTitle(propertyDTO.title());
        property.setDescription(propertyDTO.description());
        property.setAddress(propertyDTO.address());
        property.setPrice(propertyDTO.price());
        property.setBedrooms(propertyDTO.bedrooms());
        property.setBathrooms(propertyDTO.bathrooms());
        property.setArea(propertyDTO.area());

        return propertyRepository.save(property);
    }

    @Override
    @Transactional
    public void deleteProperty(Long id) {
        Property property = findById(id);
        propertyRepository.delete(property);
    }
} 