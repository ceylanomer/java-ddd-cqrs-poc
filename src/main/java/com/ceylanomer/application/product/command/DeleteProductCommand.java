package com.ceylanomer.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.ceylanomer.common.command.Command;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductCommand implements Command {
    private UUID id;
} 