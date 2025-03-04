package com.ceylanomer.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import com.ceylanomer.common.entity.AbstractAuditingEntity;

/**
 * JPA entity for Product
 */
@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductEntity extends AbstractAuditingEntity {
    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean active;
} 