package com.ceylanomer.common.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Query bus
 * Query'leri ilgili handler'lara yönlendirir
 */
@Service
@Slf4j
public class QueryBus {
    
    private final Map<Class<? extends Query>, QueryHandler> handlers = new HashMap<>();
    private final ApplicationContext applicationContext;
    
    @Autowired
    public QueryBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    /**
     * Automatically registers handlers
     */
    @PostConstruct
    public void registerHandlers() {
        Map<String, QueryHandler> handlerBeans = applicationContext.getBeansOfType(QueryHandler.class);
        
        handlerBeans.forEach((beanName, handler) -> {
            Class<?> handlerClass = handler.getClass();
            Class<?>[] typeArguments = org.springframework.core.GenericTypeResolver.resolveTypeArguments(handlerClass, QueryHandler.class);
            
            if (typeArguments != null && typeArguments.length > 0) {
                @SuppressWarnings("unchecked")
                Class<? extends Query> queryType = (Class<? extends Query>) typeArguments[0];
                handlers.put(queryType, handler);
                log.info("Registered query handler: {} for query: {}", handlerClass.getSimpleName(), queryType.getSimpleName());
            }
        });
    }
    
    /**
     * Query'i işler ve sonucu döner
     */
    @SuppressWarnings("unchecked")
    public <Q extends Query<R>, R> R execute(Q query) {
        Class<? extends Query> queryClass = query.getClass();
        QueryHandler<Q, R> handler = handlers.get(queryClass);
        
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for query: " + queryClass.getSimpleName());
        }
        
        log.debug("Executing query: {}", queryClass.getSimpleName());
        return handler.handle(query);
    }
} 