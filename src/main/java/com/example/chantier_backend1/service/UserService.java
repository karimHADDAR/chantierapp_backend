package com.example.chantier_backend1.service;

import com.example.chantier_backend1.entity.Role;
import com.example.chantier_backend1.entity.User;
import com.example.chantier_backend1.repository.RoleRepository;
import com.example.chantier_backend1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public User saveUser(String username, String password, String roleName) {
        if (userRepo.existsByUsername(username)) {
            return null;
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRoles(new HashSet<>());
        user.getRoles().add(role);

        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())) // No "ROLE_" prefix
                        .toList()
        );
    }
}
