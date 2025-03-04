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
 * Handler for UpdateProductCommand
 */
@Service
@Slf4j
public class UpdateProductCommandHandler extends CommandHandler<UpdateProductCommand, Product> {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public UpdateProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Ürün günceller
     */
    @Override
    @Transactional
    protected Product handle(UpdateProductCommand command) {
        log.debug("Updating product with id: {}", command.getId());
        
        // Repository'den ürünü bul
        Product product = productRepository.findById(command.getId())
                .orElseThrow(() -> new ApiDataNotFoundException("product.not.found", command.getId().toString()));
        
        // Domain entity'sini güncelle
        product.updateName(command.getName());
        product.updatePrice(command.getPrice());
        
        // Repository aracılığıyla kaydet
        return productRepository.save(product);
    }
} 