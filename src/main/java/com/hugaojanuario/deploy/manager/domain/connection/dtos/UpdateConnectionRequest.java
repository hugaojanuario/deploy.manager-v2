package com.hugaojanuario.deploy.manager.domain.connection.dtos;

import com.hugaojanuario.deploy.manager.domain.connection.enums.ConnectionType;

public record UpdateConnectionRequest(String userMachine,
                                      String passwordMachine,
                                      String userDb,
                                      String passwordDb,
                                      String idRemoteConnection,
                                      String passwordRemoteConnection,
                                      ConnectionType connectionType) {
}
