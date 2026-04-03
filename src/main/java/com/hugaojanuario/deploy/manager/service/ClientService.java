package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.client.Client;
import com.hugaojanuario.deploy.manager.domain.client.dtos.ClientResponse;
import com.hugaojanuario.deploy.manager.domain.client.dtos.CreateClientRequest;
import com.hugaojanuario.deploy.manager.domain.client.dtos.UpdateClientRequest;
import com.hugaojanuario.deploy.manager.domain.version.Version;
import com.hugaojanuario.deploy.manager.repository.ClientRepository;
import com.hugaojanuario.deploy.manager.repository.VersionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final VersionRepository versionRepository;

    @Transactional
    public ClientResponse createClient(CreateClientRequest request){
        Version version = versionRepository.findById(request.versionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        return new ClientResponse(client);
    }

    public List<ClientResponse> findClientByName(String name){
        return clientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ClientResponse::new)
                .toList();
    }

    @Transactional
    public ClientResponse updateClient (UUID id, UpdateClientRequest request){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        if (request.versionId() != null){
            Version version = versionRepository.findById(request.versionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found"));
            client.setActualVersion(version);
        }

        if (request.contact() != null){
            client.setContact(request.contact());
        }

        Client updated = clientRepository.save(client);

        return new ClientResponse(updated);
    }

    public void softDeleteClient(UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        client.setActivate(false);
        clientRepository.save(client);
    }
}
