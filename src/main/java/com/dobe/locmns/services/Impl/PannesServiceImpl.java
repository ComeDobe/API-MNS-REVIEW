package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.PannesDto;
import com.dobe.locmns.models.Pannes;
import com.dobe.locmns.repositories.PannesRepository;
import com.dobe.locmns.services.PannesService;
import com.dobe.locmns.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PannesServiceImpl implements PannesService {
    private final PannesRepository pannesRepository;
    private ObjectsValidator<PannesDto> validator;

    @Override
    public Integer save(PannesDto dto) {
        validator.validate(dto);
        Pannes pannes = PannesDto.toEntity(dto);
        return pannesRepository.save(pannes).getId();
    }

    @Override
    public Integer update(PannesDto dto, Integer localisationId) {
        Pannes pannes = PannesDto.toEntity(dto);
        return pannesRepository.save(pannes).getId();
    }

    @Override
    public List<PannesDto> findAll() {
        return   pannesRepository.findAll()
                .stream()
                .map(PannesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PannesDto findById(Integer id) {
        return pannesRepository.findById(id)
                .map(PannesDto::fromEntity)
                .orElse(null);
    }

    @Override
    public Void delete(Integer id) {
        Pannes pannes = pannesRepository.findById(id)
                .orElse(null);
        pannesRepository.delete(pannes);
        return null;
    }
}
