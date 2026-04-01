package com.hugaojanuario.deploy.manager.controller;

import com.hugaojanuario.deploy.manager.domain.client.dtos.CreateClientRequest;
import com.hugaojanuario.deploy.manager.domain.client.dtos.UpdateClientRequest;
import com.hugaojanuario.deploy.manager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deploy/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity createClient (@RequestBody CreateClientRequest request, UriComponentsBuilder uriComponentsBuilder){

        var newClient = clientService.createClient(request);
        var uri = uriComponentsBuilder.path("/deploy/client/{id}").buildAndExpand(newClient.id()).toUri();

        return ResponseEntity.created(uri).body(newClient);
    }

    @GetMapping
    public ResponseEntity findALlClients(@PageableDefault(size = 5) Pageable pageable){
        var find = clientService.findAllClients(pageable);

        return ResponseEntity.ok(find);
    }

    @GetMapping("/{id}")
    public ResponseEntity findClientById(@PathVariable UUID id){
        var findByid = clientService.findClientById(id);

        return ResponseEntity.ok(findByid);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable UUID id, @RequestBody UpdateClientRequest request){
        var updated = clientService.updateClient(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteClient (@PathVariable UUID id){
        clientService.softDeleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
