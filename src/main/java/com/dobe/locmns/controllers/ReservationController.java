package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.ReservationDto;
import com.dobe.locmns.dto.UtilisateurDto;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.ReservationRepository;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.ReservationService;
import com.dobe.locmns.services.UtilisateurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final MaterielService materielService;
    private final UtilisateurService utilisateurService;

    @PostMapping("/reserve/{materielId}")
    public ResponseEntity<Integer> reserveMateriel(@PathVariable int materielId, @RequestBody ReservationDto reservationDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        try {
            reservationDto.getMateriel().setId(materielId);
            UtilisateurDto utilisateur = utilisateurService.findByEmail(email);
            reservationDto.setUtilisateur(utilisateur);

            Integer reservationId = reservationService.save(reservationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateReservation(@PathVariable Integer id, @RequestBody ReservationDto reservationDto) {
        reservationDto.setId(id);
        return ResponseEntity.ok(reservationService.update(reservationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<Integer> validateReservation(@RequestBody Integer id) {
        return ResponseEntity.ok(reservationService.validateReservation(id));
    }

    @PostMapping("/invalidate/{id}")
    public ResponseEntity<Integer> invalidateReservation(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.invalidateReservation(id));
    }

}
