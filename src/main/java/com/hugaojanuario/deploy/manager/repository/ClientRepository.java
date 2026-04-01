package com.hugaojanuario.deploy.manager.repository;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Page<Client> findByActivateTrue(Pageable pageable);
}
