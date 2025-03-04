package com.ceylanomer.common.command;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.ceylanomer.common.domain.BaseAggregate;
import com.ceylanomer.common.domain.DomainEventPublisher;

/**
 * Command handler abstract sınıfı
 * Tüm command handler'lar bu sınıfı extend etmelidir
 */
@Setter
@Slf4j
public abstract class CommandHandler<C extends Command, R extends BaseAggregate> {
    
    @Autowired
    private DomainEventPublisher domainEventPublisher;
    
    /**
     * Command'i işler
     */
    protected abstract R handle(C command);
    
    /**
     * Command'i işler ve domain event'leri yayınlar
     */
    public R process(C command) {
        R result = handleCommand(command);
        domainEventPublisher.publish(result.getDomainEvents());
        result.clearEvents();
        return result;
    }
    
    /**
     * Command'i işler
     */
    protected R handleCommand(C command) {
        log.debug("Handling command: {}", command.getClass().getSimpleName());
        return handle(command);
    }
} 