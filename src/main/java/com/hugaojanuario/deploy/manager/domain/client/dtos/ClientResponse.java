package com.hugaojanuario.deploy.manager.domain.client.dtos;

import com.hugaojanuario.deploy.manager.domain.client.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(UUID id, UUID versionId, String numberVersion, String name, String city, String state, String contact, LocalDateTime createdAt) {
    public ClientResponse(Client client){
        this(client.getId(), client.getActualVersion().getId(), client.getActualVersion().getNumberVersion(), client.getName(), client.getCity(), client.getState(), client.getContact(), client.getCreatedAt());
    }
}
