package com.ceylanomer.domain.product;

import com.ceylanomer.common.domain.BaseAggregate;
import com.ceylanomer.common.exception.ApiBusinessException;
import com.ceylanomer.domain.product.event.ProductActivatedEvent;
import com.ceylanomer.domain.product.event.ProductCreatedEvent;
import com.ceylanomer.domain.product.event.ProductDeactivatedEvent;
import com.ceylanomer.domain.product.event.ProductNameUpdatedEvent;
import com.ceylanomer.domain.product.event.ProductPriceUpdatedEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product Aggregate Root
 * DDD prensiplerine uygun olarak, tüm iş mantığı bu aggregate içinde yer alır.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA için gerekli
public class Product extends BaseAggregate {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean active;

    /**
     * Yeni bir Product oluşturur
     */
    public static Product create(String name, BigDecimal price) {
        if (name == null || name.trim().isEmpty()) {
            throw new ApiBusinessException("product.name.empty");
        }
        
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiBusinessException("product.price.invalid");
        }
        
        Product product = new Product();
        product.id = UUID.randomUUID();
        product.name = name;
        product.price = price;
        product.active = true;
        
        // Domain event'i kaydet
        product.registerEvent(new ProductCreatedEvent(product.id, name, price));
        
        return product;
    }
    
    /**
     * Ürün fiyatını günceller
     */
    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiBusinessException("product.price.invalid");
        }
        
        BigDecimal oldPrice = this.price;
        this.price = newPrice;
        
        // Domain event'i kaydet
        registerEvent(new ProductPriceUpdatedEvent(this.id, oldPrice, newPrice));
    }
    
    /**
     * Ürün adını günceller
     */
    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new ApiBusinessException("product.name.empty");
        }
        
        String oldName = this.name;
        this.name = newName;
        
        // Domain event'i kaydet
        registerEvent(new ProductNameUpdatedEvent(this.id, oldName, newName));
    }
    
    /**
     * Ürünü aktif eder
     */
    public void activate() {
        if (!this.active) {
            this.active = true;
            
            // Domain event'i kaydet
            registerEvent(new ProductActivatedEvent(this.id));
        }
    }
    
    /**
     * Ürünü deaktif eder
     */
    public void deactivate() {
        if (this.active) {
            this.active = false;
            
            // Domain event'i kaydet
            registerEvent(new ProductDeactivatedEvent(this.id));
        }
    }
    
    @Override
    public UUID getId() {
        return this.id;
    }
} 