package com.hugaojanuario.deploy.manager.domain.version.dtos;

import java.time.LocalDate;

public record CreateVersionRequest (LocalDate dateRelease,
                                    String numberVersion,
                                    String changelog) {
}
