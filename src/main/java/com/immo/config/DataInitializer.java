package com.immo.config;

import com.immo.entity.Property;
import com.immo.entity.User;
import com.immo.repository.PropertyRepository;
import com.immo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(PropertyRepository propertyRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(admin);
        }

        // Create normal user if not exists
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            userRepository.save(user);
        }

        // Add sample properties if none exist
        if (propertyRepository.count() == 0) {
            User admin = userRepository.findByUsername("admin").orElseThrow();

            // Property 1
            Property property1 = new Property();
            property1.setTitle("Modern Apartment in City Center");
            property1.setDescription("Beautiful modern apartment with great views of the city. Recently renovated with high-end finishes.");
            property1.setAddress("123 Main Street, City Center");
            property1.setPrice(new BigDecimal("1500.00"));
            property1.setBedrooms(2);
            property1.setBathrooms(1);
            property1.setArea(85.0);
            property1.setOwner(admin);
            property1.setCreatedAt(LocalDateTime.now());
            property1.setUpdatedAt(LocalDateTime.now());
            propertyRepository.save(property1);

            // Property 2
            Property property2 = new Property();
            property2.setTitle("Spacious Family House");
            property2.setDescription("Large family house with garden, perfect for families. Located in a quiet neighborhood.");
            property2.setAddress("456 Oak Avenue, Suburbs");
            property2.setPrice(new BigDecimal("2500.00"));
            property2.setBedrooms(4);
            property2.setBathrooms(2);
            property2.setArea(200.0);
            property2.setOwner(admin);
            property2.setCreatedAt(LocalDateTime.now());
            property2.setUpdatedAt(LocalDateTime.now());
            propertyRepository.save(property2);

            // Property 3
            Property property3 = new Property();
            property3.setTitle("Cozy Studio Apartment");
            property3.setDescription("Perfect studio apartment for singles or couples. Located near public transportation.");
            property3.setAddress("789 Pine Street, Downtown");
            property3.setPrice(new BigDecimal("800.00"));
            property3.setBedrooms(1);
            property3.setBathrooms(1);
            property3.setArea(45.0);
            property3.setOwner(admin);
            property3.setCreatedAt(LocalDateTime.now());
            property3.setUpdatedAt(LocalDateTime.now());
            propertyRepository.save(property3);
        }
    }
} 