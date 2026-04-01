package com.hugaojanuario.deploy.manager.domain.connection.dtos;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import com.hugaojanuario.deploy.manager.domain.connection.enums.ConnectionType;

public record CreateConnectionRequest(Client client,
                                      String userMachine,
                                      String passwordMachine,
                                      String userDb,
                                      String passwordDb,
                                      String idRemoteConnection,
                                      String passwordRemoteConnection,
                                      ConnectionType connectionType) {
}
