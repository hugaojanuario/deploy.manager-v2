package com.hugaojanuario.deploy.manager.domain.user.enums;

public enum UserType {
    USER("user"),
    ADMIN("admin");

    private String role;

    UserType(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
