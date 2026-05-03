package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.*;
import com.uaiou.core.domain.gateway.DeliveryGateway;
import com.uaiou.core.domain.gateway.OrderGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDeliveryStatusUseCaseTest {

    @Mock
    private DeliveryGateway deliveryGateway;

    @Mock
    private OrderGateway orderGateway;

    private UpdateDeliveryStatusUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateDeliveryStatusUseCase(deliveryGateway, orderGateway);
    }

    @Test
    @DisplayName("Should mark delivery and orders as delivered when status is FINISHED")
    void shouldMarkDeliveredWhenFinished() {
        Delivery delivery = buildDeliveryWithSingleOrder();

        when(deliveryGateway.findById(delivery.getId())).thenReturn(Optional.of(delivery));
        when(deliveryGateway.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Delivery result = useCase.execute(delivery.getId(), DeliveryStatusEnum.FINISHED);

        assertThat(result.getStatus()).isEqualTo(DeliveryStatusEnum.FINISHED);
        assertThat(result.isFinished()).isTrue();
        assertThat(result.getDeliveredAt()).isNotNull();
        assertThat(result.getDeliveryOrders().getFirst().isDelivered()).isTrue();

        verify(orderGateway, never()).save(any(Order.class));
        verify(deliveryGateway).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should unlink and mark orders as not delivered when status is CANCELED")
    void shouldUnlinkOrdersWhenCanceled() {
        Delivery delivery = buildDeliveryWithSingleOrder();

        when(deliveryGateway.findById(delivery.getId())).thenReturn(Optional.of(delivery));
        when(deliveryGateway.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderGateway.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(delivery.getId(), DeliveryStatusEnum.CANCELED);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderGateway, atLeastOnce()).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();
        assertThat(savedOrder.getDeliveryId()).isNull();
        assertThat(savedOrder.isDelivered()).isFalse();
    }

    private Delivery buildDeliveryWithSingleOrder() {
        User owner = User.create("owner", "owner@mail.com", "hash", "31999999999");
        Establishment establishment = Establishment.create("Loja", "12345678901234", owner);
        DeliveryPerson deliveryPerson = DeliveryPerson.create("98765432100", "CNH123", null, owner);
        Address address = Address.create("Line 1", "Line 2", "Belo Horizonte", "MG", "30110000", "Centro", null, null);
        OrderType orderType = OrderType.create("FOOD");

        Order order = Order.create("Pedido 1", OrderSpecificsEnum.THERMICHOT, address, establishment, orderType);
        Delivery delivery = Delivery.create(deliveryPerson, establishment, List.of(order), address, BigDecimal.valueOf(10.50));
        order.setDeliveryId(delivery.getId());

        return delivery;
    }
}
