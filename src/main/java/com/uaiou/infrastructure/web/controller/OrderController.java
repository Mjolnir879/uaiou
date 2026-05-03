package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.entity.OrderSpecificsEnum;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.usecase.delivery.*;
import com.uaiou.infrastructure.web.dto.request.CreateOrderRequest;
import com.uaiou.infrastructure.web.dto.response.OrderResponse;
import com.uaiou.infrastructure.web.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final OrderDtoMapper orderDtoMapper;

    public OrderController(CreateOrderUseCase createOrderUseCase, FindOrderByIdUseCase findOrderByIdUseCase, ListOrdersUseCase listOrdersUseCase, UpdateOrderUseCase updateOrderUseCase, DeleteOrderUseCase deleteOrderUseCase, OrderDtoMapper orderDtoMapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.orderDtoMapper = orderDtoMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        CreateOrderOutput output = createOrderUseCase.execute(
                new CreateOrderInput(
                        request.name(),
                        request.specifics(),
                        request.addressId(),
                        request.establishmentId(),
                        request.orderTypeCode()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDtoMapper.toResponse(findOrderByIdUseCase.execute(output.id()).orElseThrow()));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders(
            @RequestParam(required = false) UUID establishmentId,
            @RequestParam(required = false) DeliveryStatusEnum status,
            @RequestParam(required = false) Boolean delivered) {
        List<OrderResponse> response = listOrdersUseCase.execute(establishmentId, delivered, status).stream()
                .map(orderDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {
        return findOrderByIdUseCase.execute(id)
                .map(orderDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new DomainException("Order not found"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable UUID id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) OrderSpecificsEnum specifics,
            @RequestParam(required = false) UUID deliveryId) {
        // Note: For simplicity, assuming partial updates by individual parameters
        // In a real application, you might use a dedicated DTO for PATCH.
        return ResponseEntity.ok(orderDtoMapper.toResponse(updateOrderUseCase.execute(id, name, specifics, deliveryId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        deleteOrderUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
