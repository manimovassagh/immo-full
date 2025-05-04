package com.immo.repository;

import com.immo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerId(Long ownerId);

    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.owner WHERE p.id = :id")
    Optional<Property> findByIdWithOwner(@Param("id") Long id);

    @Query("SELECT p FROM Property p LEFT JOIN FETCH p.owner ORDER BY p.createdAt DESC")
    List<Property> findAllWithOwner();
} 