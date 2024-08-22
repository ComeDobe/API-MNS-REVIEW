package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.LocalisationDto;
import com.dobe.locmns.models.Localisation;
import com.dobe.locmns.repositories.LocalisationRepository;
import com.dobe.locmns.services.LocalisationService;
import com.dobe.locmns.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalisationServiceImpl implements LocalisationService {
    private final LocalisationRepository localisationRepository;
    private final ObjectsValidator<LocalisationDto> validator;


    @Override
    public Integer save(LocalisationDto dto) {
       validator.validate(dto);
        Localisation localisation = LocalisationDto.toEntity(dto);
        return localisationRepository.save(localisation).getId();
    }

    @Override
    public Integer update(LocalisationDto dto) {
        Localisation localisation = LocalisationDto.toEntity(dto);
        return localisationRepository.save(localisation).getId();
    }

    @Override
    public List<LocalisationDto> findAll() {
        return  localisationRepository.findAll()
                .stream()
                .map(LocalisationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public LocalisationDto findById(Integer id) {
        return localisationRepository.findById(id)
                .map(LocalisationDto::fromEntity)
                .orElse(null);
    }

    @Override
    public Void delete(Integer id) {
        Localisation localisation = localisationRepository.findById(id)
                .orElse(null);
        localisationRepository.delete(localisation);
        return null;
    }
}
