package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.ReservationDto;
import com.dobe.locmns.models.Utilisateur;
import com.dobe.locmns.repositories.ReservationRepository;
import com.dobe.locmns.services.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping
    public ResponseEntity<Integer> createReservation(@RequestBody ReservationDto reservationDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(reservationDto));
    }
    @GetMapping
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
    public ResponseEntity<Integer> validateReservation(@RequestBody ReservationDto reservationDto) {
        return ResponseEntity.ok(reservationService.validateReservation(reservationDto));
    }

    @PostMapping("/invalidate/{id}")
    public ResponseEntity<Integer> invalidateReservation(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.invalidateReservation(id));
    }

}
