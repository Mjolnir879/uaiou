package com.uaiou.infrastructure.config;

import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.UserGateway;
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
}
