package com.immo.controller;

import com.immo.dto.UserDTO;
import com.immo.entity.User;
import com.immo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO("", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            return "redirect:/users/register?error=" + e.getMessage();
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // TODO: Get current user from security context
        return "profile";
    }
} 