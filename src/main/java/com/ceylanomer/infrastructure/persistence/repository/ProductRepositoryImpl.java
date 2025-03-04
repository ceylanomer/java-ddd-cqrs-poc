package com.ceylanomer.infrastructure.persistence.repository;

import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;
import com.ceylanomer.infrastructure.persistence.entity.ProductEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JPA implementation of the ProductRepository interface
 */
@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    
    private final ProductJpaRepository productJpaRepository;
    
    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        ProductEntity savedEntity = productJpaRepository.save(entity);
        return toDomain(savedEntity);
    }
    
    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findByActiveTrue() {
        return productJpaRepository.findByActiveTrue().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(UUID id) {
        productJpaRepository.deleteById(id);
    }
    
    /**
     * Converts domain entity to JPA entity
     */
    private ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isActive()
        );
    }
    
    /**
     * Converts JPA entity to domain entity
     */
    private Product toDomain(ProductEntity entity) {
        Product product = Product.create(entity.getName(), entity.getPrice());
        
        // Using reflection to set private fields (not ideal for DDD, but a practical solution)
        try {
            java.lang.reflect.Field idField = Product.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(product, entity.getId());
            
            java.lang.reflect.Field activeField = Product.class.getDeclaredField("active");
            activeField.setAccessible(true);
            activeField.set(product, entity.isActive());
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to domain", e);
        }
        
        return product;
    }
} 