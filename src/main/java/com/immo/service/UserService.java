package com.immo.service;

import com.immo.dto.UserDTO;
import com.immo.entity.User;

public interface UserService {
    User registerUser(UserDTO userDTO);
    User findByUsername(String username);
    User findById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 