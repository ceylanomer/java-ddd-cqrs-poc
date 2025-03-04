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
 * In accordance with DDD principles, all business logic is contained within this aggregate.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Required for JPA
public class Product extends BaseAggregate {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean active;

    /**
     * Creates a new Product
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
        
        // Register domain event
        product.registerEvent(new ProductCreatedEvent(product.id, name, price));
        
        return product;
    }
    
    /**
     * Updates the product price
     */
    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiBusinessException("product.price.invalid");
        }
        
        BigDecimal oldPrice = this.price;
        this.price = newPrice;
        
        // Register domain event
        registerEvent(new ProductPriceUpdatedEvent(this.id, oldPrice, newPrice));
    }
    
    /**
     * Updates the product name
     */
    public void updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new ApiBusinessException("product.name.empty");
        }
        
        String oldName = this.name;
        this.name = newName;
        
        // Register domain event
        registerEvent(new ProductNameUpdatedEvent(this.id, oldName, newName));
    }
    
    /**
     * Activates the product
     */
    public void activate() {
        if (!this.active) {
            this.active = true;
            
            // Register domain event
            registerEvent(new ProductActivatedEvent(this.id));
        }
    }
    
    /**
     * Deactivates the product
     */
    public void deactivate() {
        if (this.active) {
            this.active = false;
            
            // Register domain event
            registerEvent(new ProductDeactivatedEvent(this.id));
        }
    }
    
    @Override
    public UUID getId() {
        return this.id;
    }
} 