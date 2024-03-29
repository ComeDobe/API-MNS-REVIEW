package com.dobe.locmns.services.Impl;

import com.dobe.locmns.models.Role;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.RoleRepository;
import com.dobe.locmns.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitialisationServiceImpl {
    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public void initRoleAndUser() {
       Role adminRole = createRoleIfNotFound(ROLE_ADMIN);
        Role userRole = createRoleIfNotFound(ROLE_USER);

        createUserIfNotFound("christian", "dobe", "christiandobe@hotmail.fr", "admin@pass", new HashSet<>(Arrays.asList(adminRole)));

    }

    private void createUserIfNotFound(String firstName, String lastName, String email, String password, Set<Role> roles) {
      Optional<Utilisateur> user = utilisateurRepository.findByEmail(email);
        if (user.isEmpty()) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setFirstName(firstName);
            utilisateur.setLastName(lastName);
            utilisateur.setEmail(email);
            utilisateur.setPassword(passwordEncoder.encode(password));
            utilisateur.setRole(roles);
            utilisateurRepository.save(utilisateur);
        }
    }

    private Role createRoleIfNotFound(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            newRole.setRoleDescription("Default role");
            return roleRepository.save(newRole);
        }
        return role.get();
    }


}
