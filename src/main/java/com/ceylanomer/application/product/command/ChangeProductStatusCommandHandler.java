package com.ceylanomer.application.product.command;

import com.ceylanomer.common.command.CommandHandler;
import com.ceylanomer.common.exception.ApiDataNotFoundException;
import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for ChangeProductStatusCommand
 */
@Service
@Slf4j
public class ChangeProductStatusCommandHandler extends CommandHandler<ChangeProductStatusCommand, Product> {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ChangeProductStatusCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Changes the product status
     */
    @Override
    @Transactional
    protected Product handle(ChangeProductStatusCommand command) {
        log.debug("Changing product status with id: {}, active: {}", command.getId(), command.isActive());
        
        // Find the product from repository
        Product product = productRepository.findById(command.getId())
                .orElseThrow(() -> new ApiDataNotFoundException("product.not.found", command.getId().toString()));
        
        // Update the status of the domain entity
        if (command.isActive()) {
            product.activate();
        } else {
            product.deactivate();
        }
        
        // Save through repository
        return productRepository.save(product);
    }
} 