package com.ceylanomer.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDataNotFoundException extends RuntimeException {
    
    private final String key;
    private final String[] args;
    
    public ApiDataNotFoundException(String key) {
        super(key);
        this.key = key;
        this.args = new String[]{};
    }
    
    public ApiDataNotFoundException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
} 