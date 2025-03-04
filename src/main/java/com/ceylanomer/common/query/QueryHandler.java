package com.ceylanomer.common.query;

/**
 * Query handler interface
 * All query handlers should implement this interface
 */
public interface QueryHandler<Q extends Query<R>, R> {
    
    /**
     * Processes the query and returns the result
     */
    R handle(Q query);
} 