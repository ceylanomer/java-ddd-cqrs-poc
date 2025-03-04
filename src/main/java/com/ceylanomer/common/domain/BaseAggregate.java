package com.ceylanomer.common.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Tüm aggregate'lerin temel sınıfı
 */
@Getter
public abstract class BaseAggregate {
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    /**
     * Domain event ekler
     */
    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    /**
     * Domain event'leri temizler
     */
    public void clearEvents() {
        this.domainEvents.clear();
    }
    
    /**
     * Aggregate ID'sini döner
     */
    public abstract UUID getId();
} 