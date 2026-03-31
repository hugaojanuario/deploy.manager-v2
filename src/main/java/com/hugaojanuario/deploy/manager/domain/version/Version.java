package com.hugaojanuario.deploy.manager.domain.version;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "versions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String numberVersion;
    private String changelog;
}
