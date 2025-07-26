package com.example.chantier_backend1.service;

import com.example.chantier_backend1.entity.MaterialRequest;
import com.example.chantier_backend1.repository.MaterialRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialRequestService {

    private final MaterialRequestRepository materialRequestRepository;

    public MaterialRequestService(MaterialRequestRepository materialRequestRepository) {
        this.materialRequestRepository = materialRequestRepository;
    }

    public MaterialRequest save(MaterialRequest request) {
        // Ensure child items are linked to the parent
        if (request.getItems() != null) {
            request.getItems().forEach(item -> item.setMaterialRequest(request));
        }
        return materialRequestRepository.save(request);
    }

    public List<MaterialRequest> findAll() {
        return materialRequestRepository.findAll();
    }

    public Optional<MaterialRequest> findById(Long id) {
        return materialRequestRepository.findById(id);
    }

    public void deleteById(Long id) {
        materialRequestRepository.deleteById(id);
    }
}
