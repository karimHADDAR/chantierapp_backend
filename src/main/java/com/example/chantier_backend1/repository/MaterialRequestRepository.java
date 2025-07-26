package com.example.chantier_backend1.repository;

import com.example.chantier_backend1.entity.MaterialRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Long> {
    // You can add custom query methods here if needed
}
