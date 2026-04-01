package com.hugaojanuario.deploy.manager.controller;

import com.hugaojanuario.deploy.manager.domain.version.dtos.CreateVersionRequest;
import com.hugaojanuario.deploy.manager.domain.version.dtos.UpdateVersionRequest;
import com.hugaojanuario.deploy.manager.service.VersionService;
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
@RequestMapping("/deploy/version")
public class VersionController {
    private final VersionService versionService;

    @PostMapping
    public ResponseEntity createVersion(@RequestBody @Valid CreateVersionRequest request, UriComponentsBuilder uriComponentsBuilder){

        var newVersion = versionService.createVersion(request);
        var uri = uriComponentsBuilder.path("/deploy/version/{id}").buildAndExpand(newVersion.id()).toUri();

        return ResponseEntity.created(uri).body(newVersion);
    }

    @GetMapping
    public ResponseEntity findALlVersions(@PageableDefault(size = 5) Pageable pageable){
        var find = versionService.findAllVersions(pageable);

        return ResponseEntity.ok(find);
    }

    @GetMapping("/{id}")
    public ResponseEntity findVersionById(@PathVariable UUID id){
        var findByid = versionService.findVersionById(id);

        return ResponseEntity.ok(findByid);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateVersion(@PathVariable UUID id, @Valid @RequestBody UpdateVersionRequest request){
        var updated = versionService.updateVersion(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteVersion(@PathVariable UUID id){
        versionService.softDeleteVersion(id);
        return ResponseEntity.noContent().build();
    }
}
