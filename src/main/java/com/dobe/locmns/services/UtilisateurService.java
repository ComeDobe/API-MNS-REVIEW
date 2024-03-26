package com.dobe.locmns.services;

import com.dobe.locmns.dto.Auth.AuthenticationRequest;
import com.dobe.locmns.dto.Auth.AuthenticationResponse;
import com.dobe.locmns.dto.UtilisateurDto;

public interface UtilisateurService extends AbstractService<UtilisateurDto> {

    Integer validateUser(String email, String password);
    Integer invalidateUser(String email);
    Integer updatePassword(String email, String password);
    UtilisateurDto findByEmail(String email);
    Integer update(UtilisateurDto utilisateurDto);
    AuthenticationResponse register(UtilisateurDto utilisateurDto);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
