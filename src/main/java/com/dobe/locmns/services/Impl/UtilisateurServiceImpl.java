package com.dobe.locmns.services.Impl;

import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.dto.UtilisateurDto;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.UtilisateurService;
import com.dobe.locmns.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UtilisateurServiceImpl implements UtilisateurService {


    private final UtilisateurRepository utilisateurRepository;
    private final ObjectsValidator<UtilisateurDto> validator;
    @Override
    public Integer save(UtilisateurDto dto) {
        validator.validate(dto);
        Utilisateur utilisateur = UtilisateurDto.toEntity(dto);
       return  utilisateurRepository.save(utilisateur).getId();
    }

    @Override
    public List<UtilisateurDto> findAll() {
       return utilisateurRepository.findAll()
               .stream()
               .map(UtilisateurDto::fromEntity)
               .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet Id: " + id));
    }

    @Override
    public Void delete(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet Id: " + id));
        utilisateurRepository.delete(utilisateur);
        return null;
    }

    @Override
    public Integer validateUser(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setActive(true);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();

    }

    @Override
    public Integer invalidateUser(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setActive(false);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();
    }
    @Override
    public Integer updatePassword(Integer id , String password) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur trouvé avec cet id: " + id));
        utilisateur.setPassword(password);
        utilisateurRepository.save(utilisateur);
        return utilisateur.getId();
    }
    @Override
    public Integer update(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = UtilisateurDto.toEntity(utilisateurDto);
        return utilisateurRepository.save(utilisateur).getId();
    }
    @Override
    public AuthenticationResponse register(UtilisateurDto utilisateurDto) {
        return null;
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return null;
    }
}
