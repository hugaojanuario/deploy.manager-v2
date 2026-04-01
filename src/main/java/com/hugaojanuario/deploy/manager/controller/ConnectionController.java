package com.hugaojanuario.deploy.manager.controller;

import com.hugaojanuario.deploy.manager.domain.connection.dtos.CreateConnectionRequest;
import com.hugaojanuario.deploy.manager.domain.connection.dtos.UpdateConnectionRequest;
import com.hugaojanuario.deploy.manager.service.ConnectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deploy/connection")
public class ConnectionController {
    private final ConnectionService connectionService;

    @PostMapping
    public ResponseEntity createConnection(@RequestBody @Valid CreateConnectionRequest request, UriComponentsBuilder uriComponentsBuilder){

        var newConnection = connectionService.createConnection(request);
        var uri = uriComponentsBuilder.path("/deploy/connection/{id}").buildAndExpand(newConnection.id()).toUri();

        return ResponseEntity.created(uri).body(newConnection);
    }

    @GetMapping
    public ResponseEntity findALlonnections (@PageableDefault(size = 5) Pageable pageable){
        var find = connectionService.findAllConnections(pageable);

        return ResponseEntity.ok(find);
    }

    @GetMapping("/{id}")
    public ResponseEntity findConnectionById (@PathVariable UUID id){
        var findByid = connectionService.findConnectionById(id);

        return ResponseEntity.ok(findByid);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateConnection(@PathVariable UUID id, @Valid @RequestBody UpdateConnectionRequest request){
        var updated = connectionService.updateConnection(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteConnection (@PathVariable UUID id){
        connectionService.softDeleteConnection(id);
        return ResponseEntity.noContent().build();
    }
}
