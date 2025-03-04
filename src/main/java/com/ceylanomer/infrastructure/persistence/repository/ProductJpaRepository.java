package com.ceylanomer.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceylanomer.infrastructure.persistence.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for Product
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    
    /**
     * Finds active products
     */
    List<ProductEntity> findByActiveTrue();
} 