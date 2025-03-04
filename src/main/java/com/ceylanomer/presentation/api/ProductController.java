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

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping
    public ResponseEntity<Response<UUID>> createProduct(@RequestBody CreateProductCommand command) {
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(respond(product.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UUID>> updateProduct(
            @PathVariable UUID id,
            @RequestBody UpdateProductCommand command) {
        command.setId(id);
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.ok(respond(product.getId()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Response<UUID>> changeProductStatus(
            @PathVariable UUID id,
            @RequestBody ChangeProductStatusCommand command) {
        command.setId(id);
        Product product = commandBus.executeWithResponse(command);
        return ResponseEntity.ok(respond(product.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> getProduct(
            @PathVariable UUID id) {
        GetProductByIdQuery query = new GetProductByIdQuery(id);
        ProductDto productDto = queryBus.execute(query);
        return ResponseEntity.ok(respond(productDto));
    }

    @GetMapping
    public ResponseEntity<Response<DataResponse<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetAllProductsQuery query = new GetAllProductsQuery(page, size);
        List<ProductDto> products = queryBus.execute(query);
        return ResponseEntity.ok(respond(products));
    }

    @GetMapping("/active")
    public ResponseEntity<Response<DataResponse<ProductDto>>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetActiveProductsQuery query = new GetActiveProductsQuery(page, size);
        List<ProductDto> products = queryBus.execute(query);
        return ResponseEntity.ok(respond(products));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable UUID id) {
        DeleteProductCommand command = new DeleteProductCommand(id);
        commandBus.execute(command);
        return ResponseEntity.noContent().build();
    }
}