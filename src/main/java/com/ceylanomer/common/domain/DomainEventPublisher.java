package com.ceylanomer.common.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class that publishes domain events
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DomainEventPublisher {
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    /**
     * Publishes domain events
     */
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }
    
    /**
     * Publishes a single domain event
     */
    public void publish(DomainEvent event) {
        log.debug("Publishing domain event: {}", event.getEventType());
        applicationEventPublisher.publishEvent(event);
    }
} 