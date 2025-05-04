package com.immo.controller;

import com.immo.dto.PropertyDTO;
import com.immo.entity.Property;
import com.immo.entity.User;
import com.immo.repository.PropertyRepository;
import com.immo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clear properties before each test
        propertyRepository.deleteAll();
    }

    @Test
    public void testAccessPropertyCreationPageWithoutLogin() throws Exception {
        // Try to access property creation page without login
        mockMvc.perform(get("/properties/new"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAccessPropertyCreationPageWithLogin() throws Exception {
        // Try to access property creation page with login
        mockMvc.perform(get("/properties/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("properties/form"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCreateProperty() throws Exception {
        // First, ensure the user exists in the database
        User testUser = userRepository.findByUsername("user")
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername("user");
                newUser.setEmail("user@example.com");
                newUser.setPassword("user123"); // In real app, this would be encoded
                return userRepository.save(newUser);
            });

        // Create a test property
        mockMvc.perform(post("/properties")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("title", "Test Property")
                .param("description", "Test Description")
                .param("address", "Test Address")
                .param("price", "1000.00")
                .param("bedrooms", "2")
                .param("bathrooms", "1")
                .param("area", "80.0"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/properties?created"));

        // Verify the property was created
        Property createdProperty = propertyRepository.findAll().get(0);
        assertNotNull(createdProperty);
        assertEquals("Test Property", createdProperty.getTitle());
        assertEquals(testUser.getId(), createdProperty.getOwner().getId());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testCreatePropertyWithInvalidData() throws Exception {
        // Test with invalid data (missing required fields)
        mockMvc.perform(post("/properties")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("title", "") // Empty title should fail validation
                .param("description", "Test Description")
                .param("address", "Test Address")
                .param("price", "1000.00"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("/properties/new?error=*"));
    }
} 