package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import com.hugaojanuario.deploy.manager.domain.client.dtos.UpdateClientRequest;
import com.hugaojanuario.deploy.manager.domain.connection.Connection;
import com.hugaojanuario.deploy.manager.domain.connection.dtos.ConnectionResponse;
import com.hugaojanuario.deploy.manager.domain.connection.dtos.CreateConnectionRequest;
import com.hugaojanuario.deploy.manager.domain.connection.dtos.UpdateConnectionRequest;
import com.hugaojanuario.deploy.manager.repository.ClientRepository;
import com.hugaojanuario.deploy.manager.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final ClientRepository clientRepository;

    public ConnectionResponse createConnection(CreateConnectionRequest request){
        Client client = clientRepository.findById(request.client().getId())
                .orElseThrow(() -> new RuntimeException());

        Connection connection = new Connection();
        connection.setActivate(true);
        connection.setClient(client);
        connection.setUserMachine(request.userMachine());
        connection.setPasswordMachine(request.passwordMachine());
        connection.setUserDb(request.userDb());
        connection.setPasswordDb(request.passwordDb());
        connection.setIdRemoteConnection(request.idRemoteConnection());
        connection.setPasswordRemoteConnection(request.passwordRemoteConnection());
        connection.setConnectionType(request.connectionType());

        Connection saved = connectionRepository.save(connection);

        return new ConnectionResponse(saved);
    }

    public Page <ConnectionResponse> findAllConnections(Pageable pageable){
        return connectionRepository.findByActivateTrue(pageable).map(ConnectionResponse::new);
    }

    public ConnectionResponse findConnectionById(UUID id){
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new ConnectionResponse(connection);
    }

    public ConnectionResponse updateConnection(UUID id, UpdateConnectionRequest request){
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if(request.userMachine() != null){
            connection.setUserDb(request.userDb());
        }
        if(request.passwordMachine() != null){
            connection.setPasswordMachine(request.passwordMachine());
        }
        if(request.userDb() != null){
            connection.setUserDb(request.userDb());
        }
        if(request.passwordDb() != null){
            connection.setPasswordDb(request.passwordDb());
        }
        if(request.idRemoteConnection() != null){
            connection.setIdRemoteConnection(request.idRemoteConnection());
        }
        if(request.passwordRemoteConnection() != null){
            connection.setPasswordRemoteConnection(request.passwordRemoteConnection());
        }
        if(request.connectionType() != null){
            connection.setConnectionType(request.connectionType());
        }

        Connection updated = connectionRepository.save(connection);

        return new ConnectionResponse(updated);
    }

    public void softDeleteConnection(UUID id){
        Connection connection = connectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        connection.setActivate(false);

        connectionRepository.save(connection);
    }
}
