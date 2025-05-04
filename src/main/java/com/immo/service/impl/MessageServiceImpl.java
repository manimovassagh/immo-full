package com.immo.service.impl;

import com.immo.dto.MessageDTO;
import com.immo.entity.Message;
import com.immo.entity.Property;
import com.immo.entity.User;
import com.immo.repository.MessageRepository;
import com.immo.service.MessageService;
import com.immo.service.PropertyService;
import com.immo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    @Override
    @Transactional
    public Message sendMessage(MessageDTO messageDTO, Long senderId) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(messageDTO.receiverId());
        Property property = propertyService.findById(messageDTO.propertyId());

        Message message = new Message();
        message.setContent(messageDTO.content());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setProperty(property);

        return messageRepository.save(message);
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + id));
    }

    @Override
    public List<Message> findBySenderId(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    @Override
    public List<Message> findByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    @Override
    public List<Message> findByPropertyId(Long propertyId) {
        return messageRepository.findByPropertyId(propertyId);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        Message message = findById(id);
        message.setRead(true);
        messageRepository.save(message);
    }
} 