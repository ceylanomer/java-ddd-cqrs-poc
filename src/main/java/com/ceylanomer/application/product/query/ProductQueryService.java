package com.ceylanomer.application.product.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Query service for Product
 * In accordance with the CQRS pattern, a separate service for read operations
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    
    private final ProductRepository productRepository;
    
    /**
     * Finds a product by ID
     */
    public Optional<ProductDto> findById(UUID id) {
        return productRepository.findById(id)
                .map(this::mapToDto);
    }
    
    /**
     * Lists all products
     */
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Lists active products
     */
    public List<ProductDto> findActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Converts Product entity to DTO
     */
    private ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isActive()
        );
    }
} 