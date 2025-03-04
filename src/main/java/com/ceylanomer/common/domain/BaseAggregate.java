package com.ceylanomer.common.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Base class for all aggregates
 */
@Getter
public abstract class BaseAggregate {
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    /**
     * Adds a domain event
     */
    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    /**
     * Clears all domain events
     */
    public void clearEvents() {
        this.domainEvents.clear();
    }
    
    /**
     * Returns the aggregate ID
     */
    public abstract UUID getId();
} 