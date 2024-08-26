package com.dobe.locmns.services.Auth;

import com.dobe.locmns.config.JwtUtils;
import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
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
@Lazy
@RequiredArgsConstructor
public class JwtService implements UserDetailsService {
    private final JwtUtils jwtUtils;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationManager authenticationManager;

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

        return AuthenticationResponse.builder()
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .role(roles)
                .token(newGeneratedToken)
                .build();
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findById(Integer.valueOf(email))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found with email: " + email));

        return new User(
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                getAuthority(utilisateur)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(Utilisateur utilisateur) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        utilisateur.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
