package com.hugaojanuario.deploy.manager.service;

import com.hugaojanuario.deploy.manager.domain.version.Version;
import com.hugaojanuario.deploy.manager.domain.version.dtos.CreateVersionRequest;
import com.hugaojanuario.deploy.manager.domain.version.dtos.UpdateVersionRequest;
import com.hugaojanuario.deploy.manager.domain.version.dtos.VersionResponse;
import com.hugaojanuario.deploy.manager.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;

    public VersionResponse createVersion (CreateVersionRequest request){
        Version version = new Version();

        version.setActivate(true);
        version.setDateRelease(request.dateRelease());
        version.setNumberVersion(request.numberVersion());
        version.setChangelog(request.changelog());

        Version saved = versionRepository.save(version);

        return new VersionResponse(saved);
    }

    private Page <VersionResponse> findAllVersions(Pageable pageable){
        return versionRepository.findByActivateTrue(pageable).map(VersionResponse::new);

    }

    private VersionResponse findVersionById(UUID id){
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new VersionResponse(version);
    }

    private VersionResponse updateVersion(UUID id, UpdateVersionRequest request){
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if (request.changelog() != null){
            version.setChangelog(request.changelog());
        }

        Version updated = versionRepository.save(version);

        return new VersionResponse(updated);
    }

    private void softDeleteVersion(UUID id){
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        version.setActivate(false);
        versionRepository.save(version);
    }
}
