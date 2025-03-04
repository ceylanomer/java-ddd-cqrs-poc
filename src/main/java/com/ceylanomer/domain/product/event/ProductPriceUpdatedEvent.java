package com.ceylanomer.domain.product.event;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

import com.ceylanomer.common.domain.AbstractDomainEvent;

/**
 * Ürün fiyatı güncellendiğinde yayınlanan event
 */
@Getter
public class ProductPriceUpdatedEvent extends AbstractDomainEvent {
    private final UUID productId;
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;
    
    public ProductPriceUpdatedEvent(UUID productId, BigDecimal oldPrice, BigDecimal newPrice) {
        super();
        this.productId = productId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
} 