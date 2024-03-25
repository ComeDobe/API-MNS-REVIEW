package com.dobe.locmns.dto;

import com.dobe.locmns.models.Role;
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
    private String roleName;
    private String roleDescription;

    public static RoleDto fromEntity(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .roleDescription(role.getRoleDescription())
                .build();
    }
    public static Role toEntity(RoleDto role) {
        return Role.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .roleDescription(role.getRoleDescription())
                .build();

    }


}
