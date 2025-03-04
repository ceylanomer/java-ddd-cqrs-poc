package com.ceylanomer.application.product.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product için Data Transfer Object
 * Query sonuçlarında kullanılır
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean active;
} 