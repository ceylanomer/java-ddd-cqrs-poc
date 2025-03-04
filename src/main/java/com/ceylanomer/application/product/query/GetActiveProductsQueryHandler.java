package com.ceylanomer.application.product.query;

import com.ceylanomer.common.query.QueryHandler;
import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetActiveProductsQuery
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetActiveProductsQueryHandler implements QueryHandler<GetActiveProductsQuery, List<ProductDto>> {
    
    private final ProductRepository productRepository;
    
    @Override
    public List<ProductDto> handle(GetActiveProductsQuery query) {
        log.debug("Getting active products");
        
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isActive()
        );
    }
} 