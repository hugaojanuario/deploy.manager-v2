package com.hugaojanuario.deploy.manager.domain.client.dtos;



import java.util.UUID;

public record CreateClientRequest(UUID versionId,
                                  String name,
                                  String city,
                                  String state,
                                  String contact) {
}
