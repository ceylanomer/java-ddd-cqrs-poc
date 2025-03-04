package com.ceylanomer.application.product.query;

import com.ceylanomer.common.query.QueryHandler;
import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * GetProductByIdQuery için handler
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetProductByIdQueryHandler implements QueryHandler<GetProductByIdQuery, ProductDto> {
    
    private final ProductRepository productRepository;
    
    @Override
    public ProductDto handle(GetProductByIdQuery query) {
        log.debug("Getting product with id: {}", query.getId());
        
        Optional<Product> productOptional = productRepository.findById(query.getId());
        
        return productOptional
                .map(this::mapToDto)
                .orElse(null);
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