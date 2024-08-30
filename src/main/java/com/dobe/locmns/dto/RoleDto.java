package com.dobe.locmns.dto;

import com.dobe.locmns.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RoleDto {

    private Integer id;
    @NotBlank(message = "Le nom du rôle ne doit pas être vide")
    private String roleName;
    @NotBlank(message = "La description du rôle ne doit pas être vide")
    private String roleDescription;
    public static RoleDto fromEntity(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }

        Role role = new Role();
        role.setId(roleDto.getId());
        role.setRoleName(roleDto.getRoleName());
        role.setRoleDescription(roleDto.getRoleDescription());

        return role;
    }
}
