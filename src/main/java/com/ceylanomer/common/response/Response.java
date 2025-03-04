package com.ceylanomer.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response wrapper")
public class Response<T> {
    
    @Schema(description = "Error information if request failed")
    private ErrorResponse error;
    
    @Schema(description = "Response data if request succeeded")
    private T data;
    
    public Response() {
    }
    
    public Response(ErrorResponse error) {
        this.error = error;
    }
    
    public Response(T data) {
        this.data = data;
    }
    
    public ErrorResponse getError() {
        return error;
    }
    
    public T getData() {
        return data;
    }
} 