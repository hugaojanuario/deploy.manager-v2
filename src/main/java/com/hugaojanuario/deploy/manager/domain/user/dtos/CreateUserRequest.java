package com.hugaojanuario.deploy.manager.domain.user.dtos;

import com.hugaojanuario.deploy.manager.domain.user.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(@Email @Pattern(
                                        regexp = "^[a-zA-Z0-9._%+\\-]+@siplan\\.com\\.br$",
                                        message = "O email deve ser do domínio @siplan.com.br"
                                )
                                String email,
                                String password,
                                UserType userType) {
}
