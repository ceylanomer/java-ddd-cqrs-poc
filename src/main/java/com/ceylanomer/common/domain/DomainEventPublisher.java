package com.ceylanomer.common.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Domain event'leri yayınlayan sınıf
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DomainEventPublisher {
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    /**
     * Domain event'leri yayınlar
     */
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }
    
    /**
     * Tek bir domain event'i yayınlar
     */
    public void publish(DomainEvent event) {
        log.debug("Publishing domain event: {}", event.getEventType());
        applicationEventPublisher.publishEvent(event);
    }
} 