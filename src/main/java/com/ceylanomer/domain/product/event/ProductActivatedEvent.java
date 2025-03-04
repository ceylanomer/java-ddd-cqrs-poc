package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Ürün aktif edildiğinde yayınlanan event
 */
@Getter
public class ProductActivatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    
    public ProductActivatedEvent(UUID productId) {
        super();
        this.productId = productId;
    }
} 