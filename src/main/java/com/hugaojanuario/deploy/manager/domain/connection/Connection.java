package com.hugaojanuario.deploy.manager.domain.connection;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import com.hugaojanuario.deploy.manager.domain.connection.enums.ConnectionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "connections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, foreignKey = @ForeignKey(name = "fk_connection_client"))
    private Client client;

    private String userMachine;
    private String passwordMachine;
    private String userDb;
    private String passwordDb;
    private String idRemoteConnection;
    private String passwordRemoteConnection;
    private ConnectionType connectionType;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private boolean activate;
}
