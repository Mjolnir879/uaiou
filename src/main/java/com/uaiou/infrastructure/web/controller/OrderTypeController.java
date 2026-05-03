package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.usecase.delivery.FindAllOrderTypesUseCase;
import com.uaiou.core.usecase.delivery.FindOrderTypeByCodeUseCase;
import com.uaiou.infrastructure.web.dto.response.OrderTypeResponse;
import com.uaiou.infrastructure.web.mapper.OrderTypeDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-types")
public class OrderTypeController {

    private final FindAllOrderTypesUseCase findAllOrderTypesUseCase;
    private final FindOrderTypeByCodeUseCase findOrderTypeByCodeUseCase;
    private final OrderTypeDtoMapper orderTypeDtoMapper;

    public OrderTypeController(FindAllOrderTypesUseCase findAllOrderTypesUseCase, FindOrderTypeByCodeUseCase findOrderTypeByCodeUseCase, OrderTypeDtoMapper orderTypeDtoMapper) {
        this.findAllOrderTypesUseCase = findAllOrderTypesUseCase;
        this.findOrderTypeByCodeUseCase = findOrderTypeByCodeUseCase;
        this.orderTypeDtoMapper = orderTypeDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderTypeResponse>> getAllOrderTypes() {
        List<OrderTypeResponse> response = findAllOrderTypesUseCase.execute().stream()
                .map(orderTypeDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<OrderTypeResponse> getOrderTypeByCode(@PathVariable String code) {
        return findOrderTypeByCodeUseCase.execute(code)
                .map(orderTypeDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new DomainException("Order Type not found"));
    }
}
