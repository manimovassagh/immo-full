package com.immo.controller;

import com.immo.dto.MessageDTO;
import com.immo.entity.Message;
import com.immo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String listMessages(Model model) {
        // TODO: Get current user ID from security context
        Long currentUserId = 1L; // Temporary
        List<Message> sentMessages = messageService.findBySenderId(currentUserId);
        List<Message> receivedMessages = messageService.findByReceiverId(currentUserId);
        
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
            // TODO: Get current user ID from security context
            Long currentUserId = 1L; // Temporary
            Message message = messageService.sendMessage(messageDTO, currentUserId);
            return "redirect:/messages/" + message.getId() + "?sent";
        } catch (IllegalArgumentException e) {
            return "redirect:/properties/" + messageDTO.propertyId() + "?error=" + e.getMessage();
        }
    }
} 