package com.immo.controller;

import com.immo.dto.PropertyDTO;
import com.immo.entity.Property;
import com.immo.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

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
        model.addAttribute("property", new PropertyDTO("", "", "", null, null, null, null));
        return "properties/create";
    }

    @PostMapping
    public String createProperty(@ModelAttribute("property") PropertyDTO propertyDTO) {
        try {
            // TODO: Get current user ID from security context
            Long currentUserId = 1L; // Temporary
            propertyService.createProperty(propertyDTO, currentUserId);
            return "redirect:/properties?created";
        } catch (IllegalArgumentException e) {
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
            property.getArea()
        );
        model.addAttribute("property", propertyDTO);
        return "properties/edit";
    }

    @PostMapping("/{id}")
    public String updateProperty(@PathVariable Long id, @ModelAttribute("property") PropertyDTO propertyDTO) {
        try {
            propertyService.updateProperty(id, propertyDTO);
            return "redirect:/properties/" + id + "?updated";
        } catch (IllegalArgumentException e) {
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