package com.ceylanomer.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import com.ceylanomer.common.command.Command;

/**
 * Command to update a product
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand implements Command {
    private UUID id;
    private String name;
    private BigDecimal price;
} 