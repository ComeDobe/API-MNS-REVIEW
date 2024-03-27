package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.RoleDto;
import com.dobe.locmns.repositories.RoleRepository;
import com.dobe.locmns.services.RoleService;
import com.dobe.locmns.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository  roleRepository;
    private ObjectsValidator<RoleDto> validator;
    @Override
    public Integer save(RoleDto dto) {
        return null;
    }

    @Override
    public Integer update(RoleDto dto) {
        return null;
    }

    @Override
    public List<RoleDto> findAll() {
        return null;
    }

    @Override
    public RoleDto findById(Integer id) {
        return null;
    }

    @Override
    public Void delete(Integer id) {
        return null;
    }

}
