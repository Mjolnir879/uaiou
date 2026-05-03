package com.uaiou.infrastructure.config;

import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.*;
import com.uaiou.core.usecase.delivery.*;
import com.uaiou.core.usecase.user.CreateUserUseCase;
import com.uaiou.core.usecase.user.FindUserByIdUseCase;
import com.uaiou.core.usecase.user.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Bean configuration.
 * Wires use cases as Spring beans WITHOUT polluting the core layer with @Service annotations.
 * This keeps the domain/use-case layer completely framework-agnostic.
 */
@Configuration
public class BeanConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway) {
        return new FindUserByIdUseCase(userGateway);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserGateway userGateway,
                                                    DeliveryPersonGateway deliveryPersonGateway,
                                                    EstablishmentGateway establishmentGateway) {
        return new RegisterUserUseCase(userGateway, deliveryPersonGateway, establishmentGateway);
    }

    @Bean
    public FindAllOrderTypesUseCase findAllOrderTypesUseCase(OrderTypeGateway orderTypeGateway) {
        return new FindAllOrderTypesUseCase(orderTypeGateway);
    }

    @Bean
    public FindOrderTypeByCodeUseCase findOrderTypeByCodeUseCase(OrderTypeGateway orderTypeGateway) {
        return new FindOrderTypeByCodeUseCase(orderTypeGateway);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway, AddressGateway addressGateway, EstablishmentGateway establishmentGateway, OrderTypeGateway orderTypeGateway) {
        return new CreateOrderUseCase(orderGateway, addressGateway, establishmentGateway, orderTypeGateway);
    }

    @Bean
    public FindOrderByIdUseCase findOrderByIdUseCase(OrderGateway orderGateway) {
        return new FindOrderByIdUseCase(orderGateway);
    }

    @Bean
    public ListOrdersUseCase listOrdersUseCase(OrderGateway orderGateway) {
        return new ListOrdersUseCase(orderGateway);
    }

    @Bean
    public UpdateOrderUseCase updateOrderUseCase(OrderGateway orderGateway) {
        return new UpdateOrderUseCase(orderGateway);
    }

    @Bean
    public DeleteOrderUseCase deleteOrderUseCase(OrderGateway orderGateway) {
        return new DeleteOrderUseCase(orderGateway);
    }

    @Bean
    public CreateDeliveryUseCase createDeliveryUseCase(DeliveryGateway deliveryGateway, DeliveryPersonGateway deliveryPersonGateway, EstablishmentGateway establishmentGateway, OrderGateway orderGateway, AddressGateway addressGateway) {
        return new CreateDeliveryUseCase(deliveryGateway, deliveryPersonGateway, establishmentGateway, orderGateway, addressGateway);
    }

    @Bean
    public FindDeliveryByIdUseCase findDeliveryByIdUseCase(DeliveryGateway deliveryGateway) {
        return new FindDeliveryByIdUseCase(deliveryGateway);
    }

    @Bean
    public ListDeliveriesUseCase listDeliveriesUseCase(DeliveryGateway deliveryGateway) {
        return new ListDeliveriesUseCase(deliveryGateway);
    }

    @Bean
    public UpdateDeliveryStatusUseCase updateDeliveryStatusUseCase(DeliveryGateway deliveryGateway, OrderGateway orderGateway) {
        return new UpdateDeliveryStatusUseCase(deliveryGateway, orderGateway);
    }

    @Bean
    public UpdateDeliveryPaidStatusUseCase updateDeliveryPaidStatusUseCase(DeliveryGateway deliveryGateway) {
        return new UpdateDeliveryPaidStatusUseCase(deliveryGateway);
    }

    @Bean
    public CreateEvaluationUseCase createEvaluationUseCase(EvaluationGateway evaluationGateway, EstablishmentGateway establishmentGateway, DeliveryPersonGateway deliveryPersonGateway) {
        return new CreateEvaluationUseCase(evaluationGateway, establishmentGateway, deliveryPersonGateway);
    }

    @Bean
    public FindEvaluationByIdUseCase findEvaluationByIdUseCase(EvaluationGateway evaluationGateway) {
        return new FindEvaluationByIdUseCase(evaluationGateway);
    }

    @Bean
    public ListEvaluationsUseCase listEvaluationsUseCase(EvaluationGateway evaluationGateway) {
        return new ListEvaluationsUseCase(evaluationGateway);
    }
}
