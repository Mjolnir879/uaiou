package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.usecase.user.CreateUserInput;
import com.uaiou.core.usecase.user.CreateUserOutput;
import com.uaiou.infrastructure.web.dto.request.CreateUserRequest;
import com.uaiou.infrastructure.web.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {

    CreateUserInput toInput(CreateUserRequest request);

    UserResponse toResponse(CreateUserOutput output);
}
