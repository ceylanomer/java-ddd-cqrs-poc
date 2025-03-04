package com.ceylanomer.common.query;

/**
 * Query handler interface
 * Tüm query handler'lar bu interface'i implement etmelidir
 */
public interface QueryHandler<Q extends Query<R>, R> {
    
    /**
     * Query'i işler ve sonucu döner
     */
    R handle(Q query);
} 