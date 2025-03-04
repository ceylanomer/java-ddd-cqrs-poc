package com.ceylanomer.common.command;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceylanomer.common.domain.BaseAggregate;
import com.ceylanomer.common.domain.DomainEventPublisher;

/**
 * Command handler abstract class
 * All command handlers should extend this class
 */
@Setter
@Slf4j
public abstract class CommandHandler<C extends Command, R extends BaseAggregate> {
    
    @Autowired
    private DomainEventPublisher domainEventPublisher;
    
    /**
     * Handles the command
     */
    protected abstract R handle(C command);
    
    /**
     * Processes the command and publishes domain events
     */
    public R process(C command) {
        R result = handleCommand(command);
        domainEventPublisher.publish(result.getDomainEvents());
        result.clearEvents();
        return result;
    }
    
    /**
     * Handles the command and logs any exceptions
     */
    protected R handleCommand(C command) {
        try {
            return handle(command);
        } catch (Exception e) {
            log.error("Error handling command: {}", command.getClass().getSimpleName(), e);
            throw e;
        }
    }
} 