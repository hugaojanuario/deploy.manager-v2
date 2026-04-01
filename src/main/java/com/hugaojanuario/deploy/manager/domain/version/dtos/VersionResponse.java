package com.hugaojanuario.deploy.manager.domain.version.dtos;

import com.hugaojanuario.deploy.manager.domain.version.Version;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record VersionResponse(UUID id,
                              LocalDate dateRelease,
                              String numberVersion,
                              String changelog,
                              LocalDateTime createdAt) {
    public VersionResponse(Version version){
        this(version.getId(),
                version.getDateRelease(),
                version.getNumberVersion(),
                version.getChangelog(),
                version.getCreatedAt());
    }
}
