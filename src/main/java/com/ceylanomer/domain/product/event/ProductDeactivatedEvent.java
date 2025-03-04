package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Event published when a product is deactivated
 */
@Getter
public class ProductDeactivatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    
    public ProductDeactivatedEvent(UUID productId) {
        super();
        this.productId = productId;
    }
} 