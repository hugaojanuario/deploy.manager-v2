package com.hugaojanuario.deploy.manager.repository;


import com.hugaojanuario.deploy.manager.domain.version.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VersionRepository extends JpaRepository<Version, UUID> {
    Page<Version> findByActivateTrue(Pageable pageable);
}
