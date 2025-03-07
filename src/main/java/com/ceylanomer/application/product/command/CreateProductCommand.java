package com.ceylanomer.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.ceylanomer.common.command.Command;

/**
 * Command to create a new product
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand implements Command {
    private String name;
    private BigDecimal price;
} 