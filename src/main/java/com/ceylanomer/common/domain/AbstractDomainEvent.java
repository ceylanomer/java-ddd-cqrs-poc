package com.ceylanomer.common.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class for domain events
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