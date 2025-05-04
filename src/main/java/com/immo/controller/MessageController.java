package com.immo.controller;

import com.immo.dto.MessageDTO;
import com.immo.entity.Message;
import com.immo.entity.User;
import com.immo.service.MessageService;
import com.immo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listMessages(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        List<Message> sentMessages = messageService.findBySenderId(currentUser.getId());
        List<Message> receivedMessages = messageService.findByReceiverId(currentUser.getId());
        
        model.addAttribute("sentMessages", sentMessages);
        model.addAttribute("receivedMessages", receivedMessages);
        return "messages/list";
    }

    @GetMapping("/{id}")
    public String showMessage(@PathVariable Long id, Model model) {
        Message message = messageService.findById(id);
        messageService.markAsRead(id);
        model.addAttribute("message", message);
        return "messages/view";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute("message") MessageDTO messageDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByUsername(auth.getName());
            Message message = messageService.sendMessage(messageDTO, currentUser.getId());
            return "redirect:/messages/" + message.getId() + "?sent";
        } catch (IllegalArgumentException e) {
            return "redirect:/properties/" + messageDTO.propertyId() + "?error=" + e.getMessage();
        }
    }
} 