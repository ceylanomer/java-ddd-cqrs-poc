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
 * Product için query service
 * CQRS pattern'ine uygun olarak, okuma işlemleri için ayrı bir servis
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    
    private final ProductRepository productRepository;
    
    /**
     * ID'ye göre ürün bulur
     */
    public Optional<ProductDto> findById(UUID id) {
        return productRepository.findById(id)
                .map(this::mapToDto);
    }
    
    /**
     * Tüm ürünleri listeler
     */
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Aktif ürünleri listeler
     */
    public List<ProductDto> findActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Product entity'sini DTO'ya dönüştürür
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