package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.usecase.user.RegisterUserInput;
import com.uaiou.core.usecase.user.RegisterUserOutput;
import com.uaiou.core.usecase.user.RegisterUserUseCase;
import com.uaiou.infrastructure.web.dto.request.RegisterUserRequest;
import com.uaiou.infrastructure.web.dto.response.RegisterUserResponse;
import com.uaiou.infrastructure.web.mapper.RegisterDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegisterController {

    private final RegisterUserUseCase registerUserUseCase;
    private final RegisterDtoMapper mapper;

    public RegisterController(RegisterUserUseCase registerUserUseCase, RegisterDtoMapper mapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserInput input = mapper.toInput(request);
        RegisterUserOutput output = registerUserUseCase.execute(input);
        RegisterUserResponse response = mapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
