package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.infrastructure.web.dto.response.OrderTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderTypeDtoMapper {

    public OrderTypeResponse toResponse(OrderType orderType) {
        return new OrderTypeResponse(orderType.getCode());
    }
}
