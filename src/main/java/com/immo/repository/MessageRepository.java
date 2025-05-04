package com.immo.repository;

import com.immo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findByPropertyId(Long propertyId);

    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.sender LEFT JOIN FETCH m.receiver LEFT JOIN FETCH m.property WHERE m.id = :id")
    Optional<Message> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.sender LEFT JOIN FETCH m.receiver LEFT JOIN FETCH m.property WHERE m.sender.id = :userId OR m.receiver.id = :userId ORDER BY m.createdAt DESC")
    List<Message> findAllByUserIdWithDetails(@Param("userId") Long userId);
} 