package com.ceylanomer.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.ceylanomer.common.command.Command;

/**
 * Ürün durumunu değiştirmek için command
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProductStatusCommand implements Command {
    private UUID id;
    private boolean active;
} 