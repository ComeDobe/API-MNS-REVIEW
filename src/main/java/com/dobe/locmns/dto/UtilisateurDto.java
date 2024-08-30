package com.dobe.locmns.dto;

import com.dobe.locmns.models.Utilisateur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UtilisateurDto {

    private Integer id;
    @NotBlank(message = "le nom ne doit pas être vide")
    private String firstName;
    @NotBlank(message = "le prénom ne doit pas être vide")
    private String lastName;
    @NotBlank(message = "L'email ne doit pas être vide")
    private String email;
    @NotBlank(message = "le mot de passe ne doit pas être vide")
    @Size(min = 8, max = 16, message = "le mot de passe doit être entre 8 et 16 caractères")
    private String password;
    private Set<RoleDto> role;
    @NotBlank(message = "l'adresse ne doit pas être vide")
    private String adresse;
    @NotBlank(message = "le téléphone ne doit pas être vide")
    private String telephone;
    private boolean active;
    private boolean isAdmin;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .role(utilisateur.getRole() != null ? utilisateur.getRole().stream()
                        .map(RoleDto::fromEntity)
                        .collect(Collectors.toSet()) : null)
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .active(utilisateur.isActive())
                .isAdmin(utilisateur.isAdmin())
                .build();
    }


    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setFirstName(utilisateurDto.getFirstName());
        utilisateur.setLastName(utilisateurDto.getLastName());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setPassword(utilisateurDto.getPassword());
        utilisateur.setRole(utilisateurDto.getRole() != null ? utilisateurDto.getRole().stream()
                .map(RoleDto::toEntity)
                .collect(Collectors.toSet()) : null);
        utilisateur.setAdresse(utilisateurDto.getAdresse());
        utilisateur.setTelephone(utilisateurDto.getTelephone());
        utilisateur.setActive(utilisateurDto.isActive());
        utilisateur.setAdmin(utilisateurDto.isAdmin());

        return utilisateur;
    }
}
