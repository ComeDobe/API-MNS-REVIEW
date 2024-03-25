package com.dobe.locmns.dto;

import com.dobe.locmns.models.Utilisateur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<RoleDto> role;
    @NotBlank(message = "l'adresse ne doit pas être vide")
    private String adresse;
    @NotBlank(message = "le téléphone ne doit pas être vide")
    private String telephone;
    private boolean active;
    private boolean isAdmin;


    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .role(utilisateur.getRole()
                        .stream()
                        .map(role -> RoleDto.builder()
                                .id(role.getId())
                                .roleName(role.getRoleName())
                                .roleDescription(role.getRoleDescription())
                                .build())
                        .toList())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .build();
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateur) {
        return Utilisateur.builder()
                .id(utilisateur.getId())
                .firstName(utilisateur.getFirstName())
                .lastName(utilisateur.getLastName())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .active(utilisateur.isActive())
                .adresse(utilisateur.getAdresse())
                .telephone(utilisateur.getTelephone())
                .build();

    }
}
