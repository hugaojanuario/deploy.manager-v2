package com.hugaojanuario.deploy.manager.domain.connection.dtos;

import com.hugaojanuario.deploy.manager.domain.connection.Connection;
import com.hugaojanuario.deploy.manager.domain.connection.enums.ConnectionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConnectionResponse (UUID id,
                                  String userMachine,
                                  String passwordMachine,
                                  String userDb,
                                  String passwordDb,
                                  String idRemoteConnection,
                                  String passwordRemoteConnection,
                                  ConnectionType connectionType,
                                  LocalDateTime createdAt){
    public ConnectionResponse(Connection connection){
        this(connection.getId(),
                connection.getUserMachine(),
                connection.getPasswordMachine(),
                connection.getUserDb(),
                connection.getPasswordDb(),
                connection.getIdRemoteConnection(),
                connection.getPasswordRemoteConnection(),
                connection.getConnectionType(),
                connection.getCreatedAt());
    }
}
