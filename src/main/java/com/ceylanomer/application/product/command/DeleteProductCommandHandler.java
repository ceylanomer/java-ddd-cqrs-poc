package com.ceylanomer.application.product.command;

import com.ceylanomer.common.command.CommandHandler;
import com.ceylanomer.common.exception.ApiDataNotFoundException;
import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Handler for DeleteProductCommand
 */
@Service
@Slf4j
public class DeleteProductCommandHandler extends CommandHandler<DeleteProductCommand, Product> {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public DeleteProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Deletes a product
     */
    @Override
    @Transactional
    protected Product handle(DeleteProductCommand command) {
        log.debug("Deleting product with id: {}", command.getId());
        
        // Find the product from repository
        UUID productId = command.getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiDataNotFoundException("product.not.found", productId.toString()));
        
        // Deactivate the product
        product.deactivate();
        
        // Save through repository
        return productRepository.save(product);
    }
} 