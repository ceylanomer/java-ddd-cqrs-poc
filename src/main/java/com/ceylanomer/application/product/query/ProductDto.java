package com.ceylanomer.application.product.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for Product
 * Used in query results
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Product data transfer object")
public class ProductDto {
    
    @Schema(description = "Unique identifier of the product")
    private UUID id;
    
    @Schema(description = "Name of the product")
    private String name;
    
    @Schema(description = "Price of the product")
    private BigDecimal price;
    
    @Schema(description = "Whether the product is active")
    private boolean active;
} 