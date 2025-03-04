package com.ceylanomer.presentation.api;

import com.ceylanomer.application.product.command.ChangeProductStatusCommand;
import com.ceylanomer.application.product.command.CreateProductCommand;
import com.ceylanomer.application.product.command.DeleteProductCommand;
import com.ceylanomer.application.product.command.UpdateProductCommand;
import com.ceylanomer.application.product.query.GetActiveProductsQuery;
import com.ceylanomer.application.product.query.GetAllProductsQuery;
import com.ceylanomer.application.product.query.GetProductByIdQuery;
import com.ceylanomer.application.product.query.ProductDto;
import com.ceylanomer.common.command.CommandBus;
import com.ceylanomer.common.controller.BaseController;
import com.ceylanomer.common.query.QueryBus;
import com.ceylanomer.common.response.DataResponse;
import com.ceylanomer.common.response.Response;
import com.ceylanomer.domain.product.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController extends BaseController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<Response<UUID>> createProduct(@RequestBody CreateProductCommand command) {
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(respond(product.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<Response<UUID>> updateProduct(
            @PathVariable UUID id,
            @RequestBody UpdateProductCommand command) {
        command.setId(id);
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.ok(respond(product.getId()));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change product status")
    public ResponseEntity<Response<UUID>> changeProductStatus(
            @PathVariable UUID id,
            @RequestBody ChangeProductStatusCommand command) {
        command.setId(id);
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.ok(respond(product.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Response<ProductDto>> getProduct(
            @PathVariable UUID id) {
        GetProductByIdQuery query = new GetProductByIdQuery(id);
        ProductDto productDto = queryBus.execute(query);
        return ResponseEntity.ok(respond(productDto));
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<Response<DataResponse<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetAllProductsQuery query = new GetAllProductsQuery(page, size);
        List<ProductDto> products = queryBus.execute(query);
        return ResponseEntity.ok(respond(products));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products")
    public ResponseEntity<Response<DataResponse<ProductDto>>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetActiveProductsQuery query = new GetActiveProductsQuery(page, size);
        List<ProductDto> products = queryBus.execute(query);
        return ResponseEntity.ok(respond(products));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable UUID id) {
        DeleteProductCommand command = new DeleteProductCommand(id);
        commandBus.execute(command);
        return ResponseEntity.noContent().build();
    }
}