package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.models.Materiel;
import com.dobe.locmns.repositories.MaterielRepositroy;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterielServiceImpl implements MaterielService {

    private final MaterielRepositroy materielRepositroy;
    private final ObjectsValidator<MaterielDto> validator;

    @Override
    public Integer save(MaterielDto dto) {
        validator.validate(dto);
        Materiel materiel = MaterielDto.toEntity(dto);
        return materielRepositroy.save(materiel).getId();
    }

    @Override
    public Integer update(MaterielDto dto) {
        Materiel materiel = MaterielDto.toEntity(dto);
        return materielRepositroy.save(materiel).getId();
    }

    @Override
    public List<MaterielDto> findAll() {
        return materielRepositroy.findAll()
                .stream()
                .map(MaterielDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MaterielDto findById(Integer id) {
        return materielRepositroy.findById(id)
                .map(MaterielDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun materiel trouvé avec cet Id: " + id));
    }

    @Override
    public Void delete(Integer id) {
        Materiel materiel = materielRepositroy.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun materiel trouvé avec cet Id: " + id));
        materielRepositroy.delete(materiel);
        return null;
    }

}
