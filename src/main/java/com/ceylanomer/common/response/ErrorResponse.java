package com.ceylanomer.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response details")
public class ErrorResponse {
    
    @Schema(description = "Error code")
    private String code;
    
    @Schema(description = "Error description")
    private String description;
    
    public ErrorResponse() {
        
    }
    
    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
} 