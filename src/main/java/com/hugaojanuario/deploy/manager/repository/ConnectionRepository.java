package com.hugaojanuario.deploy.manager.repository;

import com.hugaojanuario.deploy.manager.domain.connection.Connection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends JpaRepository<Connection, UUID> {
    Page<Connection> findByActivateTrue(Pageable pageable);
    List<Connection> findByClientIdAndActivateTrue(UUID clientId);
}
