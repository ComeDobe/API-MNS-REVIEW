package com.dobe.locmns.services.Impl;

import com.dobe.locmns.config.JwtUtils;
import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.dto.UtilisateurDto;
import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.RoleRepository;
import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.UtilisateurService;
import com.dobe.locmns.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    private final UtilisateurRepository utilisateurRepository;
    private final ObjectsValidator<UtilisateurDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;
    @Override
    public Integer save(UtilisateurDto dto) {
        validator.validate(dto);
        Utilisateur utilisateur = UtilisateurDto.toEntity(dto);
       return  utilisateurRepository.save(utilisateur).getId();
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet Id: " + id));
    }



    @Override
    public Void delete(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet Id: " + id));
        utilisateurRepository.delete(utilisateur);
        return null;
    }

    @Override
    public Integer validateUser(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setActive(true);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();

    }

    @Override
    public Integer invalidateUser(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setActive(false);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();
    }
    @Override
    public Integer updatePassword(Integer id , String password) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setPassword(password);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();
    }
    @Override
    public Integer update(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = UtilisateurDto.toEntity(utilisateurDto);
        return utilisateurRepository.save(utilisateur).getId();
    }
    @Override
    @Transactional
    public AuthenticationResponse register(UtilisateurDto utilisateurDto) {
        validator.validate(utilisateurDto);
        Utilisateur utilisateur = UtilisateurDto.toEntity(utilisateurDto);
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
       if (utilisateurDto.isAdmin()) {
            utilisateur.addRole(findOrCreateRole(ROLE_ADMIN));
        } else {
            utilisateur.addRole(findOrCreateRole(ROLE_USER));

    }
   var savedUser = utilisateurRepository.save(utilisateur);
       Map<String, Object> claims = new HashMap<>();
       claims.put("utilisateurId", savedUser.getId());
       claims.put("roles", savedUser.getFirstName() + " " + savedUser.getLastName());
       String jwt = jwtUtils.generateToken(savedUser, claims);

       List<String> roles = savedUser.getRole()
               .stream()
               .map(Role::getRoleName)
               .collect(Collectors.toList());
       return AuthenticationResponse.builder()
               .token(jwt)
               .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
               .role(roles)
               .build();
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email: " + request.getEmail()));
        if (!passwordEncoder.matches(request.getPassword(), utilisateur.getPassword())) {
            throw new EntityNotFoundException("Mot de passe incorrect");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("utilisateurId", utilisateur.getId());
        claims.put("roles", utilisateur.getFirstName() + " " + utilisateur.getLastName());
        String jwt = jwtUtils.generateToken(utilisateur, claims);
        List<String> roles = utilisateur.getRole()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
        return AuthenticationResponse.builder()
                .token(jwt)
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .role(roles)
                .build();
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        validator.validateEmail(email);
        return utilisateurRepository.findByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet email: " + email));
    }


    private Role findOrCreateRole(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            return role.get();
        } else {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            return roleRepository.save(newRole);
        }
    }

}