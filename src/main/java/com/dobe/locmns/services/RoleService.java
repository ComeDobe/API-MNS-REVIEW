package com.dobe.locmns.services;

import com.dobe.locmns.dto.RoleDto;

public interface RoleService extends AbstractService <RoleDto> {
    String createNewRole(String role);

}
