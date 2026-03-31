package com.hugaojanuario.deploy.manager.domain.client.dtos;

import java.util.UUID;

public record UpdateClientRequest(UUID versionId,
                                  String contact) {
}
