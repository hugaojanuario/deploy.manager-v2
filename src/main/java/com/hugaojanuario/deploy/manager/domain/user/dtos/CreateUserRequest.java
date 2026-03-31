package com.hugaojanuario.deploy.manager.domain.user.dtos;

import com.hugaojanuario.deploy.manager.domain.user.enums.UserType;

public record CreateUserRequest(String name,
                                String email,
                                String password,
                                UserType userType) {
}
