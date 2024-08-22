package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.UtilisateurDto;
import com.dobe.locmns.repositories.UtilisateurRepository;
import com.dobe.locmns.services.UtilisateurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilisateur")
@Tag(name = "utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurRepository utilisateurRepository;


    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> save(@RequestBody UtilisateurDto utilisateurDto) {
        return ResponseEntity.ok(utilisateurService.update(utilisateurDto));

    }
    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UtilisateurDto>> findAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }
    @GetMapping("/{utilisateurId}")
    public ResponseEntity<UtilisateurDto> findById(@PathVariable Integer utilisateurId) {
        return ResponseEntity.ok(utilisateurService.findById(utilisateurId));

}
    @DeleteMapping("/{utilisateurId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer utilisateurId) {
        utilisateurService.delete(utilisateurId);
        return ResponseEntity.accepted().build();
    }
    @PatchMapping("/validate/{utilisateurId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> validateUser(@PathVariable Integer utilisateurId) {
        return ResponseEntity.ok(utilisateurService.validateUser(utilisateurId));
    }
    @PatchMapping("/invalidate/{utilisateurId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> invalidateUser(@PathVariable Integer utilisateurId) {
        return ResponseEntity.ok(utilisateurService.invalidateUser(utilisateurId));
    }
}

