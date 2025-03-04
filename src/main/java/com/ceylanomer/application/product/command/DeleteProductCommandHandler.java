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
 * DeleteProductCommand için handler
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
     * Ürün siler
     */
    @Override
    @Transactional
    protected Product handle(DeleteProductCommand command) {
        log.debug("Deleting product with id: {}", command.getId());
        
        // Repository'den ürünü bul
        UUID productId = command.getId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiDataNotFoundException("product.not.found", productId.toString()));
        
        // Ürünü deaktif et
        product.deactivate();
        
        // Repository aracılığıyla kaydet
        return productRepository.save(product);
    }
} 