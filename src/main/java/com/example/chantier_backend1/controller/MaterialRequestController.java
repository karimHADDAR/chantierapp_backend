package com.example.chantier_backend1.controller;

import com.example.chantier_backend1.entity.MaterialRequest;
import com.example.chantier_backend1.service.MaterialRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-requests")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST}) 
public class MaterialRequestController {

    private final MaterialRequestService materialRequestService;

    public MaterialRequestController(MaterialRequestService materialRequestService) {
        this.materialRequestService = materialRequestService;
    }

    @PostMapping
    public MaterialRequest createMaterialRequest(@RequestBody MaterialRequest request) {
        return materialRequestService.save(request);
    }

    @GetMapping
    public List<MaterialRequest> getAllRequests() {
        return materialRequestService.findAll();
    }
}
