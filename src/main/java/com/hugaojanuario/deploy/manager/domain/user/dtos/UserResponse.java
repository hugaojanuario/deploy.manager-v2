package com.hugaojanuario.deploy.manager.domain.user.dtos;

import com.hugaojanuario.deploy.manager.domain.user.User;
import com.hugaojanuario.deploy.manager.domain.user.enums.UserType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id,
                           String email,
                           String password,
                           UserType userType,
                           LocalDateTime createdAt) {
    public UserResponse( User user){
        this(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserType(),
                user.getCreatedAt());
    }
}
