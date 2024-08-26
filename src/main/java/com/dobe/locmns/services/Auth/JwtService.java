package com.dobe.locmns.services.Auth;

import com.dobe.locmns.config.JwtUtils;
import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationManager authenticationManager;

    public JwtService(JwtUtils jwtUtils, UtilisateurRepository utilisateurRepository, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.utilisateurRepository = utilisateurRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
        authenticate(email, password);

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found with email: " + email));

        UserDetails userDetails = loadUserByUsername(email);

        Map<String, Object> claims = new HashMap<>();
        claims.put("utilisateurId", utilisateur.getId());
        String newGeneratedToken = jwtUtils.generateToken(userDetails, claims);

        List<String> roles = utilisateur.getRole().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        logger.info("JWT token created for user: {}. Roles: {}", email, roles);

        return AuthenticationResponse.builder()
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .role(roles)
                .token(newGeneratedToken)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user details for email: {}", email);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found with email: " + email));

        Set<SimpleGrantedAuthority> authorities = getAuthority(utilisateur);
        logger.info("User found with authorities: {}", authorities);

        return new User(utilisateur.getEmail(), utilisateur.getPassword(), authorities
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(Utilisateur utilisateur) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        utilisateur.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().replace("ROLE_", "")));
        });
        return authorities;
    }


    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            logger.info("User authenticated successfully: {}", email);
        } catch (DisabledException e) {
            logger.error("USER_DISABLED: {}", email);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("INVALID_CREDENTIALS for user: {}", email);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
