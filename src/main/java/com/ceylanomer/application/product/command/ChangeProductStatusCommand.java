package com.ceylanomer.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.ceylanomer.common.command.Command;

/**
 * Command to change product status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProductStatusCommand implements Command {
    private UUID id;
    private boolean active;
} 