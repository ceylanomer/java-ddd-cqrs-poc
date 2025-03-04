package com.ceylanomer.application.product.query;

import java.util.List;

import com.ceylanomer.common.query.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Aktif ürünleri getirmek için query
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetActiveProductsQuery implements Query<List<ProductDto>> {
    private int page;
    private int size;
} 