package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.PretDto;
import com.dobe.locmns.models.Pret;
import com.dobe.locmns.repositories.PretRepository;
import com.dobe.locmns.services.PretService;
import com.dobe.locmns.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PretServiceImpl implements PretService {
    private final PretRepository pretRepository;
    private ObjectsValidator<PretDto> validator;


    @Override
    public Integer save(PretDto dto) {
         validator.validate(dto);
         Pret pret = PretDto.toEntity(dto);
         return pretRepository.save(pret).getId();
    }

    @Override
    public Integer update(PretDto dto, Integer localisationId) {
        Pret pret = PretDto.toEntity(dto);
        return pretRepository.save(pret).getId();
    }

    @Override
    public List<PretDto> findAll() {
        return  pretRepository.findAll()
                .stream()
                .map(PretDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PretDto findById(Integer id) {
        return pretRepository.findById(id)
                .map(PretDto::fromEntity)
                .orElse(null);
    }

    @Override
    public Void delete(Integer id) {
        Pret pret = pretRepository.findById(id)
                .orElse(null);
        pretRepository.delete(pret);
        return null;
    }
}
