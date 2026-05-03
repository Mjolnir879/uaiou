package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.OrderSpecificsEnum;

import java.util.UUID;

public record CreateOrderInput(
        String name,
        OrderSpecificsEnum specifics,
        UUID addressId,
        UUID establishmentId,
        String orderTypeCode
) {
}
