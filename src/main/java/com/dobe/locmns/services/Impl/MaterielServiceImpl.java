package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.models.Materiel;
import com.dobe.locmns.models.Pannes;

import com.dobe.locmns.repositories.MaterielRepositry;
import com.dobe.locmns.repositories.PannesRepository;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterielServiceImpl implements MaterielService {

    private final MaterielRepositry materielRepositry;
    private final PannesRepository pannesRepository;
    private final ObjectsValidator<MaterielDto> validator;

    @Override
    public Integer save(MaterielDto dto) {
        validator.validate(dto);
        Materiel materiel = MaterielDto.toEntity(dto);
        return materielRepositry.save(materiel).getId();
    }

    @Override
    public Integer update(MaterielDto dto) {
        Materiel materiel = MaterielDto.toEntity(dto);
        return materielRepositry.save(materiel).getId();
    }

    @Override
    public List<MaterielDto> findAll() {
        return materielRepositry.findAll()
                .stream()
                .map(MaterielDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MaterielDto findById(Integer id) {
        return materielRepositry.findById(id)
                .map(MaterielDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun materiel trouvé avec cet Id: " + id));
    }

    @Transactional
    @Override
    public Void delete(Integer materielId) {
        List<Pannes> pannes = pannesRepository.findByMaterielId(materielId);
        if (!pannes.isEmpty()) {
            throw new IllegalArgumentException("Ce matériel est utilisé dans une panne.");
        }
        Materiel materiel = materielRepositry.findById(materielId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun matériel trouvé avec cet ID: " + materielId));

        materielRepositry.delete(materiel);
        return null;
    }


}
