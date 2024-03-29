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
    @NotBlank(message = "Le roleName ne peut pas etre vide")
    private String roleName;
    @NotBlank(message = "La description ne peut pas etre vide")
    private String roleDescription;

    public static RoleDto fromEntity(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .roleDescription(role.getRoleDescription())
                .build();
    }
    public static Role toEntity(RoleDto roleDto) {
        Role.RoleBuilder roleBuilder = Role.builder()
                .roleName(roleDto.getRoleName())
                .roleDescription(roleDto.getRoleDescription());

        if (roleDto.getId() != null) {
            roleBuilder.id(roleDto.getId());
        }

        return roleBuilder.build();
    }
}
