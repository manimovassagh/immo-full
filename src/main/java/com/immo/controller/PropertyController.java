package com.immo.controller;

import com.immo.dto.PropertyDTO;
import com.immo.entity.Property;
import com.immo.entity.User;
import com.immo.service.PropertyService;
import com.immo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    private final Path photoStorageLocation = Paths.get("src/main/resources/static/photos");

    @GetMapping
    public String listProperties(Model model) {
        List<Property> properties = propertyService.findAll();
        model.addAttribute("properties", properties);
        return "properties/list";
    }

    @GetMapping("/{id}")
    public String showProperty(@PathVariable Long id, Model model) {
        Property property = propertyService.findById(id);
        model.addAttribute("property", property);
        return "properties/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("property", PropertyDTO.createEmpty());
        return "properties/form";
    }

    @PostMapping
    public String createProperty(@Valid @ModelAttribute("property") PropertyDTO propertyDTO, 
                               @RequestParam("photo") MultipartFile photo,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "properties/form";
        }
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByUsername(auth.getName());
            
            // Save the photo
            if (photo != null && !photo.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Files.copy(photo.getInputStream(), photoStorageLocation.resolve(fileName));
                propertyDTO = new PropertyDTO(
                    propertyDTO.title(),
                    propertyDTO.description(),
                    propertyDTO.address(),
                    propertyDTO.price(),
                    propertyDTO.bedrooms(),
                    propertyDTO.bathrooms(),
                    propertyDTO.area(),
                    fileName
                );
            }
            
            propertyService.createProperty(propertyDTO, currentUser.getId());
            return "redirect:/properties?created";
        } catch (IllegalArgumentException | IOException e) {
            return "redirect:/properties/new?error=" + e.getMessage();
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Property property = propertyService.findById(id);
        PropertyDTO propertyDTO = new PropertyDTO(
            property.getTitle(),
            property.getDescription(),
            property.getAddress(),
            property.getPrice(),
            property.getBedrooms(),
            property.getBathrooms(),
            property.getArea(),
            property.getPhotoFileName()
        );
        model.addAttribute("property", propertyDTO);
        return "properties/form";
    }

    @PostMapping("/{id}")
    public String updateProperty(@PathVariable Long id, 
                               @Valid @ModelAttribute("property") PropertyDTO propertyDTO,
                               @RequestParam("photo") MultipartFile photo,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "properties/form";
        }
        
        try {
            // Save the photo if a new one is uploaded
            if (photo != null && !photo.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
                Files.copy(photo.getInputStream(), photoStorageLocation.resolve(fileName));
                propertyDTO = new PropertyDTO(
                    propertyDTO.title(),
                    propertyDTO.description(),
                    propertyDTO.address(),
                    propertyDTO.price(),
                    propertyDTO.bedrooms(),
                    propertyDTO.bathrooms(),
                    propertyDTO.area(),
                    fileName
                );
            }
            
            propertyService.updateProperty(id, propertyDTO);
            return "redirect:/properties/" + id + "?updated";
        } catch (IllegalArgumentException | IOException e) {
            return "redirect:/properties/" + id + "/edit?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return "redirect:/properties?deleted";
        } catch (IllegalArgumentException e) {
            return "redirect:/properties/" + id + "?error=" + e.getMessage();
        }
    }
} 