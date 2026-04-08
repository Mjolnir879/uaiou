package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.User;
import com.uaiou.core.domain.exception.EmailAlreadyExistsException;
import com.uaiou.core.domain.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    private CreateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateUserUseCase(userGateway);
    }

    @Test
    @DisplayName("Should create a user successfully when email is unique")
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserInput input = new CreateUserInput("johndoe", "john@email.com", "+5511999999999");

        when(userGateway.existsByEmail(anyString())).thenReturn(false);
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        CreateUserOutput output = useCase.execute(input);

        // Then
        assertThat(output).isNotNull();
        assertThat(output.id()).isNotNull();
        assertThat(output.username()).isEqualTo("johndoe");
        assertThat(output.email()).isEqualTo("john@email.com");
        assertThat(output.phoneNumber()).isEqualTo("+5511999999999");
        assertThat(output.active()).isTrue();
        assertThat(output.createdAt()).isNotNull();

        verify(userGateway).existsByEmail("john@email.com");
        verify(userGateway).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException when email is duplicated")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        CreateUserInput input = new CreateUserInput("johndoe", "john@email.com", "+5511999999999");

        when(userGateway.existsByEmail("john@email.com")).thenReturn(true);

        // When / Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("john@email.com");

        verify(userGateway).existsByEmail("john@email.com");
        verify(userGateway, never()).save(any(User.class));
    }
}
