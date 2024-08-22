package com.dobe.locmns.services;

import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.dto.UtilisateurDto;

public interface UtilisateurService extends AbstractService<UtilisateurDto> {

    Integer validateUser(Integer id);
    Integer invalidateUser(Integer id);
    Integer updatePassword(Integer id, String password);
    UtilisateurDto findById(Integer id);
    Integer update(UtilisateurDto utilisateurDto);
    AuthenticationResponse register(UtilisateurDto utilisateurDto);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
