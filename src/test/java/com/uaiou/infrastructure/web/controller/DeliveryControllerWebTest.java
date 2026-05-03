package com.uaiou.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.usecase.delivery.*;
import com.uaiou.infrastructure.web.dto.response.DeliveryResponse;
import com.uaiou.infrastructure.web.mapper.DeliveryDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeliveryController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeliveryControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateDeliveryUseCase createDeliveryUseCase;

    @MockBean
    private FindDeliveryByIdUseCase findDeliveryByIdUseCase;

    @MockBean
    private ListDeliveriesUseCase listDeliveriesUseCase;

    @MockBean
    private UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase;

    @MockBean
    private UpdateDeliveryPaidStatusUseCase updateDeliveryPaidStatusUseCase;

    @MockBean
    private DeliveryDtoMapper deliveryDtoMapper;

    @Test
    @DisplayName("Should update delivery status")
    void shouldUpdateDeliveryStatus() throws Exception {
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);

        DeliveryResponse response = new DeliveryResponse(
                deliveryId,
                10,
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(UUID.randomUUID()),
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                DeliveryStatusEnum.FINISHED,
                new BigDecimal("12.50"),
                false
        );

        when(updateDeliveryStatusUseCase.execute(eq(deliveryId), eq(DeliveryStatusEnum.FINISHED))).thenReturn(delivery);
        when(deliveryDtoMapper.toResponse(delivery)).thenReturn(response);

        mockMvc.perform(
                        patch("/deliveries/{id}/status", deliveryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new StatusPayload("FINISHED")))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deliveryId.toString()))
                .andExpect(jsonPath("$.status").value("FINISHED"))
                .andExpect(jsonPath("$.isFinished").value(true));
    }

    @Test
    @DisplayName("Should mark delivery as paid")
    void shouldMarkDeliveryAsPaid() throws Exception {
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);

        DeliveryResponse response = new DeliveryResponse(
                deliveryId,
                10,
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(UUID.randomUUID()),
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                DeliveryStatusEnum.FINISHED,
                new BigDecimal("12.50"),
                true
        );

        when(updateDeliveryPaidStatusUseCase.execute(eq(deliveryId))).thenReturn(delivery);
        when(deliveryDtoMapper.toResponse(delivery)).thenReturn(response);

        mockMvc.perform(patch("/deliveries/{id}/paid", deliveryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deliveryId.toString()))
                .andExpect(jsonPath("$.paid").value(true));
    }

    private record StatusPayload(String status) {
    }
}
