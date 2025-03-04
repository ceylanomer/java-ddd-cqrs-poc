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
     * Yeni ürün oluşturur
     */
    @Override
    @Transactional
    protected Product handle(CreateProductCommand command) {
        log.debug("Creating product with name: {}", command.getName());
        
        // Domain entity'sini oluştur
        Product product = Product.create(command.getName(), command.getPrice());
        
        // Repository aracılığıyla kaydet
        return productRepository.save(product);
    }
} 