package com.example.chantier_backend1.controller;

import com.example.chantier_backend1.security.JwtService;
import com.example.chantier_backend1.service.UserService;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        return jwtService.generateToken(userDetails.getUsername(),
                userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(java.util.stream.Collectors.toSet()));
    }
}

@Data
class AuthRequest {
    private String username;
    private String password;
    private String role;
}
