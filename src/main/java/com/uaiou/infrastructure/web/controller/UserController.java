package com.uaiou.infrastructure.web.controller;

import com.uaiou.core.usecase.user.CreateUserInput;
import com.uaiou.core.usecase.user.CreateUserOutput;
import com.uaiou.core.usecase.user.CreateUserUseCase;
import com.uaiou.core.usecase.user.FindUserByIdUseCase;
import com.uaiou.infrastructure.web.dto.request.CreateUserRequest;
import com.uaiou.infrastructure.web.dto.response.UserResponse;
import com.uaiou.infrastructure.web.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(CreateUserUseCase createUserUseCase,
                          FindUserByIdUseCase findUserByIdUseCase,
                          UserDtoMapper mapper,
                          PasswordEncoder passwordEncoder) {
        this.createUserUseCase = createUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserInput mappedInput = mapper.toInput(request);
        CreateUserInput input = new CreateUserInput(
                mappedInput.username(),
                mappedInput.email(),
                passwordEncoder.encode(request.password()),
                mappedInput.phoneNumber()
        );
        CreateUserOutput output = createUserUseCase.execute(input);
        UserResponse response = mapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id) {
        return findUserByIdUseCase.execute(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
