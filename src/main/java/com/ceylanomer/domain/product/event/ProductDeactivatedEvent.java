package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Ürün deaktif edildiğinde yayınlanan event
 */
@Getter
public class ProductDeactivatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    
    public ProductDeactivatedEvent(UUID productId) {
        super();
        this.productId = productId;
    }
} 