package com.ceylanomer.application.product.command;

import com.ceylanomer.common.command.CommandHandler;
import com.ceylanomer.domain.product.Product;
import com.ceylanomer.domain.product.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateProductCommand
 */
@Service
@Slf4j
public class CreateProductCommandHandler extends CommandHandler<CreateProductCommand, Product> {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public CreateProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Creates a new product
     */
    @Override
    @Transactional
    protected Product handle(CreateProductCommand command) {
        log.debug("Creating product with name: {}", command.getName());
        
        // Create the domain entity
        Product product = Product.create(command.getName(), command.getPrice());
        
        // Save through repository
        return productRepository.save(product);
    }
} 