package com.ceylanomer.common.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ceylanomer.common.domain.BaseAggregate;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Command bus
 * Routes commands to their respective handlers
 */
@Service
@Slf4j
public class CommandBus {
    
    private final Map<Class<? extends Command>, CommandHandler> handlers = new HashMap<>();
    private final ApplicationContext applicationContext;
    
    @Autowired
    public CommandBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    /**
     * Automatically registers handlers
     */
    @PostConstruct
    public void registerHandlers() {
        Map<String, CommandHandler> handlerBeans = applicationContext.getBeansOfType(CommandHandler.class);
        
        handlerBeans.forEach((beanName, handler) -> {
            Class<?> handlerClass = handler.getClass();
            Class<?>[] typeArguments = org.springframework.core.GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
            
            if (typeArguments != null && typeArguments.length > 0) {
                @SuppressWarnings("unchecked")
                Class<? extends Command> commandType = (Class<? extends Command>) typeArguments[0];
                handlers.put(commandType, handler);
                log.info("Registered command handler: {} for command: {}", handlerClass.getSimpleName(), commandType.getSimpleName());
            }
        });
    }
    
    /**
     * Processes the command (does not return a response)
     */
    public <C extends Command, R extends BaseAggregate> void execute(C command) {
        executeWithResponse(command);
    }
    
    /**
     * Processes the command and returns the result
     */
    @SuppressWarnings("unchecked")
    public <C extends Command, R extends BaseAggregate> R executeWithResponse(C command) {
        Class<? extends Command> commandClass = command.getClass();
        CommandHandler<C, R> handler = handlers.get(commandClass);
        
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for command: " + commandClass.getSimpleName());
        }
        
        log.debug("Executing command: {}", commandClass.getSimpleName());
        return handler.process(command);
    }
} 