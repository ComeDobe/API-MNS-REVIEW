package com.dobe.locmns.config;

import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.Auth.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class JwtServiceConfig {

    @Bean
    public JwtService jwtService(JwtUtils jwtUtils, UtilisateurRepository utilisateurRepository, AuthenticationManager authenticationManager) {
        return new JwtService(jwtUtils, utilisateurRepository, authenticationManager);
    }
}
