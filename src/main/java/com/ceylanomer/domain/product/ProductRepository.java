package com.ceylanomer.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Product repository interface
 * Domain katmanında tanımlanmış olup, infrastructure katmanında implement edilecektir.
 * Bu sayede domain katmanı, infrastructure katmanına bağımlı olmaz (Dependency Inversion).
 */
public interface ProductRepository {
    /**
     * Ürünü kaydeder veya günceller
     */
    Product save(Product product);
    
    /**
     * ID'ye göre ürün bulur
     */
    Optional<Product> findById(UUID id);
    
    /**
     * Tüm ürünleri listeler
     */
    List<Product> findAll();
    
    /**
     * Aktif ürünleri listeler
     */
    List<Product> findByActiveTrue();
    
    /**
     * Ürünü siler
     */
    void delete(UUID id);
} 