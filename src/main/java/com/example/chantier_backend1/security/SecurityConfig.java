package com.example.chantier_backend1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/**").permitAll()

                        // Use hasAuthority for your custom roles
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/test/pdg").hasAuthority("PDG")
                        .requestMatchers("/api/test/demandeur").hasAuthority("Demandeur")
                        .requestMatchers("/api/test/financier").hasAuthority("financier")
                        .requestMatchers("/api/test/Directeur_des_achats").hasAuthority("Directeur des achats")
                        .requestMatchers("/api/test/Responsable_des_achats").hasAuthority("Responsable des achats")
                        .requestMatchers("/api/test/Direction_Générale").hasAuthority("Direction Générale")
                        .requestMatchers("/api/test/Direction_des_Moyens_Généraux").hasAuthority("Direction des Moyens Généraux")


                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
