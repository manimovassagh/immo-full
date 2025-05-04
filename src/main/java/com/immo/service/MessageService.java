package com.immo.service;

import com.immo.dto.MessageDTO;
import com.immo.entity.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(MessageDTO messageDTO, Long senderId);
    Message findById(Long id);
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findByPropertyId(Long propertyId);
    void markAsRead(Long id);
} 