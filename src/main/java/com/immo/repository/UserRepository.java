package com.immo.repository;

import com.immo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.properties WHERE u.id = :id")
    Optional<User> findByIdWithProperties(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.sentMessages LEFT JOIN FETCH u.receivedMessages WHERE u.id = :id")
    Optional<User> findByIdWithMessages(@Param("id") Long id);
} 