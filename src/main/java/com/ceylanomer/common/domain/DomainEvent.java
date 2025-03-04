package com.ceylanomer.common.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event interface
 */
public interface DomainEvent {
    /**
     * Event ID'sini döner
     */
    UUID getEventId();
    
    /**
     * Event oluşturulma zamanını döner
     */
    Instant getOccurredOn();
    
    /**
     * Event tipini döner
     */
    String getEventType();
} 