package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.usecase.user.CreateUserInput;
import com.uaiou.core.usecase.user.CreateUserOutput;
import com.uaiou.infrastructure.web.dto.request.CreateUserRequest;
import com.uaiou.infrastructure.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDtoMapper {

    @Mapping(target = "passwordHash", source = "password")
    CreateUserInput toInput(CreateUserRequest request);

    UserResponse toResponse(CreateUserOutput output);
}
