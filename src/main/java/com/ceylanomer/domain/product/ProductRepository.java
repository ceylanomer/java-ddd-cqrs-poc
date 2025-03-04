package com.ceylanomer.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Product repository interface
 * Defined in the domain layer and will be implemented in the infrastructure layer.
 * This way, the domain layer does not depend on the infrastructure layer (Dependency Inversion).
 */
public interface ProductRepository {
    /**
     * Saves or updates a product
     */
    Product save(Product product);
    
    /**
     * Finds a product by ID
     */
    Optional<Product> findById(UUID id);
    
    /**
     * Lists all products
     */
    List<Product> findAll();
    
    /**
     * Lists active products
     */
    List<Product> findByActiveTrue();
    
    /**
     * Deletes a product
     */
    void delete(UUID id);
} 