package com.dobe.locmns.dto;

import com.dobe.locmns.models.Utilisateur;
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
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleDto> role;
    private String adresse;
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
