package com.ceylanomer.common.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event interface
 */
public interface DomainEvent {
    /**
     * Returns the event ID
     */
    UUID getEventId();
    
    /**
     * Returns the event creation time
     */
    Instant getOccurredOn();
    
    /**
     * Returns the event type
     */
    String getEventType();
} 