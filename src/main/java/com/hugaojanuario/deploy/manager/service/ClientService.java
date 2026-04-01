package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import com.hugaojanuario.deploy.manager.domain.client.dtos.ClientResponse;
import com.hugaojanuario.deploy.manager.domain.client.dtos.CreateClientRequest;
import com.hugaojanuario.deploy.manager.domain.client.dtos.UpdateClientRequest;
import com.hugaojanuario.deploy.manager.domain.user.dtos.UpdateUserRequest;
import com.hugaojanuario.deploy.manager.domain.version.Version;
import com.hugaojanuario.deploy.manager.repository.ClientRepository;
import com.hugaojanuario.deploy.manager.repository.VersionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final VersionRepository versionRepository;

    @Transactional
    public ClientResponse createClient(CreateClientRequest request){
        Version version = versionRepository.findById(request.versionId())
                .orElseThrow(() -> new RuntimeException());

        Client client = new Client();
        client.setActivate(true);
        client.setActualVersion(version);
        client.setName(request.name());
        client.setCity(request.city());
        client.setState(request.state());
        client.setContact(request.contact());

        Client saved = clientRepository.save(client);

        return new ClientResponse(saved);
    }

    public Page <ClientResponse> findAllClients(Pageable pageable){
        return clientRepository.findByActivateTrue(pageable).map(ClientResponse :: new);
    }

    public ClientResponse findClientById (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new ClientResponse(client);
    }

    @Transactional
    public ClientResponse updateClient (UUID id, UpdateClientRequest request){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if (request.versionId() != null){
            Version version = versionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Version not found . . ."));
            client.setActualVersion(version);
        }

        if (request.contact() != null){
            client.setContact(request.contact());
        }

        Client updated = clientRepository.save(client);

        return new ClientResponse(updated);
    }

    @Transactional
    public void softDeleteClient(UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        client.setActivate(false);
        clientRepository.save(client);
    }
}
