package com.ceylanomer.application.product.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.ceylanomer.common.query.Query;

/**
 * ID'ye göre ürün getirmek için query
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductByIdQuery implements Query<ProductDto> {
    private UUID id;
} 