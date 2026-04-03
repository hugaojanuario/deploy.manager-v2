package com.hugaojanuario.deploy.manager.domain.user.dtos;

import com.hugaojanuario.deploy.manager.domain.user.User;

public record AuthRegisterResponse(String email) {
    public AuthRegisterResponse (User user){
        this(user.getEmail());
    }
    
}
