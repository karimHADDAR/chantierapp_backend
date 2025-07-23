package com.example.chantier_backend1;

import com.example.chantier_backend1.entity.Role;
import com.example.chantier_backend1.repository.RoleRepository;
import com.example.chantier_backend1.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ChantierBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(ChantierBackend1Application.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserService userService) {
        return args -> {
            List<String> roles = List.of(
                "Demandeur",
                "Acheteur/financier",
                "Responsable_des_achats",
                "Directeur_des_achats",
                "Direction_Générale",
                "Direction_des_Moyens_Généraux",
                "PDG"
            );

            // Insert roles
            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(new Role(null, roleName));
                }
            }

            // Create default users
            userService.saveUser("admin", "admin123", "PDG");
            userService.saveUser("user", "user123", "Demandeur");
        };
    }
}
