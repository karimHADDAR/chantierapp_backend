package com.example.chantier_backend1.controller;

import com.example.chantier_backend1.security.JwtService;
import com.example.chantier_backend1.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        return userService.saveUser(request.getUsername(), request.getPassword(), request.getRole()) != null
                ? "User registered successfully"
                : "Username already exists";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        // Authentification de l'utilisateur
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Chargement des détails de l'utilisateur
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());

        // Génération du token JWT
        String token = jwtService.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toSet())
        );

        // Création de la réponse JSON
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", userDetails.getUsername());
        response.put("roles", userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList()));

        return response;
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
    private String role; // utilisé uniquement pour /signup
}
