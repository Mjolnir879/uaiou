package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.usecase.user.RegisterUserInput;
import com.uaiou.core.usecase.user.RegisterUserOutput;
import com.uaiou.infrastructure.web.dto.request.RegisterUserRequest;
import com.uaiou.infrastructure.web.dto.response.RegisterUserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RegisterDtoMapper {

    RegisterUserInput toInput(RegisterUserRequest request);

    RegisterUserResponse toResponse(RegisterUserOutput output);
}
