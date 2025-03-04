package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Ürün adı güncellendiğinde yayınlanan event
 */
@Getter
public class ProductNameUpdatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    private final String oldName;
    private final String newName;
    
    public ProductNameUpdatedEvent(UUID productId, String oldName, String newName) {
        super();
        this.productId = productId;
        this.oldName = oldName;
        this.newName = newName;
    }
} 