package com.dobe.locmns.services.Auth;

import com.dobe.locmns.config.JwtUtils;
import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
@RequiredArgsConstructor
public class JwtService implements UserDetailsService {
    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse createJwtToken(AuthenticationRequest authenticationRequest) throws Exception {
        String email = authenticationRequest.getEmail();
        authenticate(email, authenticationRequest.getPassword());

        Utilisateur utilisateur = findUserByEmail(email);
        UserDetails userDetails = loadUserByUsername(email);

        String token = jwtUtils.generateToken(userDetails, Map.of("utilisateurId", utilisateur.getId()));
        List<String> roles = utilisateur.getRole().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return AuthenticationResponse.builder()
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .role(roles)
                .token(token)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = findUserByEmail(email);
        Set<SimpleGrantedAuthority> authorities = getAuthority(utilisateur);
        return new User(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
    }

    private Set<SimpleGrantedAuthority> getAuthority(Utilisateur utilisateur) {
        return utilisateur.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().replace("ROLE_", "")))
                .collect(Collectors.toSet());
    }

    private void authenticate(String email, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private Utilisateur findUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found with email: " + email));
    }
}
