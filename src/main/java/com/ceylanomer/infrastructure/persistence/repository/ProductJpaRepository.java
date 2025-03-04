package com.ceylanomer.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceylanomer.infrastructure.persistence.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

/**
 * Product için Spring Data JPA repository
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    
    /**
     * Aktif ürünleri bulur
     */
    List<ProductEntity> findByActiveTrue();
} 