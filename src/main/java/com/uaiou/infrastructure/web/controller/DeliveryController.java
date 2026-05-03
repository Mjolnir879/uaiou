package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.usecase.delivery.*;
import com.uaiou.infrastructure.web.dto.request.CreateDeliveryRequest;
import com.uaiou.infrastructure.web.dto.request.UpdateDeliveryStatusRequest;
import com.uaiou.infrastructure.web.dto.response.DeliveryResponse;
import com.uaiou.infrastructure.web.mapper.DeliveryDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final FindDeliveryByIdUseCase findDeliveryByIdUseCase;
    private final ListDeliveriesUseCase listDeliveriesUseCase;
    private final UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;
    private final UpdateDeliveryPaidStatusUseCase updateDeliveryPaidStatusUseCase;
    private final DeliveryDtoMapper deliveryDtoMapper;

    public DeliveryController(CreateDeliveryUseCase createDeliveryUseCase, FindDeliveryByIdUseCase findDeliveryByIdUseCase, ListDeliveriesUseCase listDeliveriesUseCase, UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase, UpdateDeliveryPaidStatusUseCase updateDeliveryPaidStatusUseCase, DeliveryDtoMapper deliveryDtoMapper) {
        this.createDeliveryUseCase = createDeliveryUseCase;
        this.findDeliveryByIdUseCase = findDeliveryByIdUseCase;
        this.listDeliveriesUseCase = listDeliveriesUseCase;
        this.updateDeliveryStatusUseCase = updateDeliveryStatusUseCase;
        this.updateDeliveryPaidStatusUseCase = updateDeliveryPaidStatusUseCase;
        this.deliveryDtoMapper = deliveryDtoMapper;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid CreateDeliveryRequest request) {
        CreateDeliveryOutput output = createDeliveryUseCase.execute(
                new CreateDeliveryInput(
                        request.deliveryPersonId(),
                        request.establishmentId(),
                        request.orderIds(),
                        request.addressId(),
                        request.value()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryDtoMapper.toResponse(findDeliveryByIdUseCase.execute(output.id()).orElseThrow()));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> listDeliveries(
            @RequestParam(required = false) UUID deliveryPersonId,
            @RequestParam(required = false) UUID establishmentId,
            @RequestParam(required = false) DeliveryStatusEnum status) {
        List<DeliveryResponse> response = listDeliveriesUseCase.execute(deliveryPersonId, establishmentId, status).stream()
                .map(deliveryDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable UUID id) {
        return findDeliveryByIdUseCase.execute(id)
                .map(deliveryDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new DomainException("Delivery not found"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(@PathVariable UUID id, @RequestBody @Valid UpdateDeliveryStatusRequest request) {
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(updateDeliveryStatusUseCase.execute(id, request.status())));
    }

    @PatchMapping("/{id}/paid")
    public ResponseEntity<DeliveryResponse> markDeliveryAsPaid(@PathVariable UUID id) {
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(updateDeliveryPaidStatusUseCase.execute(id)));
    }
}
