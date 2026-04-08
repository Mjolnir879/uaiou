package com.uaiou.infrastructure.config;

import com.uaiou.core.domain.gateway.UserGateway;
import com.uaiou.core.usecase.user.CreateUserUseCase;
import com.uaiou.core.usecase.user.FindUserByIdUseCase;
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
}
