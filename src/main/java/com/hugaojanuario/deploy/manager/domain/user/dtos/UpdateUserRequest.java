package com.hugaojanuario.deploy.manager.domain.user.dtos;

import com.hugaojanuario.deploy.manager.domain.user.enums.UserType;

public record UpdateUserRequest(String password,
                                UserType userType) {
}
