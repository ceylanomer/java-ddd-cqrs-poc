package com.ceylanomer.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiBusinessException extends RuntimeException {
    
    private final String key;
    private final String[] args;
    
    public ApiBusinessException(String key) {
        super(key);
        this.key = key;
        this.args = new String[]{};
    }
    
    public ApiBusinessException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
} 