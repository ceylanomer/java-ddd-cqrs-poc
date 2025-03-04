package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Event published when a product is activated
 */
@Getter
public class ProductActivatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    
    public ProductActivatedEvent(UUID productId) {
        super();
        this.productId = productId;
    }
} 