package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.RoleDto;
import com.dobe.locmns.models.Role;
import com.dobe.locmns.repositories.RoleRepository;
import com.dobe.locmns.services.RoleService;
import com.dobe.locmns.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository  roleRepository;
    private ObjectsValidator<RoleDto> validator;

    @Override
    @Transactional
    public Integer save(RoleDto dto) {
        validator.validate(dto);

        if (dto.getId() != null) {
            roleRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Role with ID " + dto.getId() + " not found"));
        } else {
            roleRepository.findByRoleName(dto.getRoleName())
                    .ifPresent(role -> {
                        throw new IllegalArgumentException("Role with name " + dto.getRoleName() + " already exists");
                    });
        }
        Role newRole = RoleDto.toEntity(dto);
        Role savedRole = roleRepository.save(newRole);
        return savedRole.getId();
    }


    @Override
    public Integer update(RoleDto dto) {
        Role role = roleRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("No role found with the ID : " + dto.getId()));
        return roleRepository.save(RoleDto.toEntity(dto)).getId();
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(RoleDto::fromEntity)
                .collect(java.util.stream.Collectors.toList());

    }

    @Override
    public RoleDto findById(Integer id) {
        return roleRepository.findById(id)
                .map(RoleDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No role found with the ID : " + id));
    }

    @Override
    public Void delete(Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No role found with the ID : " + id));
        roleRepository.delete(role);
        return null;
    }

}
