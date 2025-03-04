package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Event published when a product is created
 */
@Getter
public class ProductCreatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    private final String name;
    private final BigDecimal price;
    
    public ProductCreatedEvent(UUID productId, String name, BigDecimal price) {
        super();
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
} 