package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.usecase.delivery.CreateEvaluationInput;
import com.uaiou.core.usecase.delivery.CreateEvaluationOutput;
import com.uaiou.core.usecase.delivery.CreateEvaluationUseCase;
import com.uaiou.core.usecase.delivery.FindEvaluationByIdUseCase;
import com.uaiou.core.usecase.delivery.ListEvaluationsUseCase;
import com.uaiou.infrastructure.web.dto.request.CreateEvaluationRequest;
import com.uaiou.infrastructure.web.dto.response.EvaluationResponse;
import com.uaiou.infrastructure.web.mapper.EvaluationDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final CreateEvaluationUseCase createEvaluationUseCase;
    private final FindEvaluationByIdUseCase findEvaluationByIdUseCase;
    private final ListEvaluationsUseCase listEvaluationsUseCase;
    private final EvaluationDtoMapper evaluationDtoMapper;

    public EvaluationController(CreateEvaluationUseCase createEvaluationUseCase, FindEvaluationByIdUseCase findEvaluationByIdUseCase, ListEvaluationsUseCase listEvaluationsUseCase, EvaluationDtoMapper evaluationDtoMapper) {
        this.createEvaluationUseCase = createEvaluationUseCase;
        this.findEvaluationByIdUseCase = findEvaluationByIdUseCase;
        this.listEvaluationsUseCase = listEvaluationsUseCase;
        this.evaluationDtoMapper = evaluationDtoMapper;
    }

    @PostMapping
    public ResponseEntity<EvaluationResponse> createEvaluation(@RequestBody @Valid CreateEvaluationRequest request) {
        CreateEvaluationOutput output = createEvaluationUseCase.execute(
                new CreateEvaluationInput(
                        request.rating(),
                        request.note(),
                        request.establishmentId(),
                        request.deliveryPersonId(),
                        request.type()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationDtoMapper.toResponse(findEvaluationByIdUseCase.execute(output.id()).orElseThrow()));
    }

    @GetMapping
    public ResponseEntity<List<EvaluationResponse>> listEvaluations(
            @RequestParam(required = false) EvaluationTypeEnum type,
            @RequestParam(required = false) UUID establishmentId,
            @RequestParam(required = false) UUID deliveryPersonId) {
        List<EvaluationResponse> response = listEvaluationsUseCase.execute(type, establishmentId, deliveryPersonId).stream()
                .map(evaluationDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationResponse> getEvaluationById(@PathVariable UUID id) {
        return findEvaluationByIdUseCase.execute(id)
                .map(evaluationDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new DomainException("Evaluation not found"));
    }
}
