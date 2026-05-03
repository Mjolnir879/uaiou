package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.*;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.EvaluationGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEvaluationUseCaseTest {

    @Mock
    private EvaluationGateway evaluationGateway;

    @Mock
    private EstablishmentGateway establishmentGateway;

    @Mock
    private DeliveryPersonGateway deliveryPersonGateway;

    private CreateEvaluationUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateEvaluationUseCase(evaluationGateway, establishmentGateway, deliveryPersonGateway);
    }

    @Test
    @DisplayName("Should create establishment evaluation and update average rating")
    void shouldCreateEstablishmentEvaluationAndUpdateRating() {
        User owner = User.create("owner", "owner@mail.com", "hash", "31999999999");
        Establishment establishment = Establishment.create("Loja", "12345678901234", owner);

        CreateEvaluationInput input = new CreateEvaluationInput(
                new BigDecimal("4.5"),
                "Muito bom",
                establishment.getId(),
                null,
                EvaluationTypeEnum.ESTABLISHMENT_EVALUATION
        );

        when(establishmentGateway.findById(establishment.getId())).thenReturn(Optional.of(establishment));
        when(evaluationGateway.save(any(Evaluation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(evaluationGateway.getAverageRatingForEstablishment(establishment.getId())).thenReturn(new BigDecimal("4.25"));

        CreateEvaluationOutput output = useCase.execute(input);

        assertThat(output.id()).isNotNull();
        assertThat(output.type()).isEqualTo(EvaluationTypeEnum.ESTABLISHMENT_EVALUATION);
        assertThat(output.establishmentId()).isEqualTo(establishment.getId());

        ArgumentCaptor<Establishment> establishmentCaptor = ArgumentCaptor.forClass(Establishment.class);
        verify(establishmentGateway).save(establishmentCaptor.capture());
        assertThat(establishmentCaptor.getValue().getRating()).isEqualTo(4.25);
    }

    @Test
    @DisplayName("Should fail when establishment id is missing for establishment evaluation")
    void shouldFailWhenEstablishmentIdMissing() {
        CreateEvaluationInput input = new CreateEvaluationInput(
                new BigDecimal("4.0"),
                "Bom",
                null,
                null,
                EvaluationTypeEnum.ESTABLISHMENT_EVALUATION
        );

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(DomainException.class)
                .hasMessage("Establishment ID is required for establishment evaluation");

        verifyNoInteractions(establishmentGateway, evaluationGateway, deliveryPersonGateway);
    }
}
