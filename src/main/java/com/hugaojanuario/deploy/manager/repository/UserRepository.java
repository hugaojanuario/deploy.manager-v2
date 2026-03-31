package com.hugaojanuario.deploy.manager.repository;

import com.hugaojanuario.deploy.manager.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
