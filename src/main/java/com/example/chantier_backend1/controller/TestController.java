package com.example.chantier_backend1.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    public String userAccess() {
        return "Hello User";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Hello Admin";
    }
    @GetMapping("/pdg")
    public String pdgOnly() {
        return "Hello PDG";
    }

    @GetMapping("/demandeur")
    public String demandeurOnly() {
        return "Hello Demandeur";
    }

    @GetMapping("/financier")
    public String financierOnly() {
        return "Hello Acheteur/financier";
    }
}
