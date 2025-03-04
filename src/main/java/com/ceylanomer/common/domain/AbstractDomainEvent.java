package com.ceylanomer.common.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event'lerin temel sınıfı
 */
@Getter
public abstract class AbstractDomainEvent implements DomainEvent {
    private final UUID eventId;
    private final Instant occurredOn;
    
    protected AbstractDomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = Instant.now();
    }
    
    @Override
    public String getEventType() {
        return this.getClass().getSimpleName();
    }
} 