package com.dobe.locmns.controllers;


import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.ReservationService;
import com.dobe.locmns.services.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materiel")
@Tag(name = "materiel")
public class MaterielController {

    private final MaterielService materielService;
    private final ReservationService reservationService;
    private final EmailService emailService;
    private final UserDetailsService userDetailsService;

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody MaterielDto materielDto) {
        return ResponseEntity.ok(materielService.save(materielDto));
    }


    @PutMapping("")
    public ResponseEntity<Integer> update(@RequestBody MaterielDto materielDto) {
        return ResponseEntity.ok(materielService.update(materielDto));

    }
    @GetMapping({"", "/{materielId}"})
    public ResponseEntity<?> getMateriel(@PathVariable(required = false) Integer materielId) {
        try {
            if (materielId != null) {
                MaterielDto materiel = materielService.findById(materielId);
                return ResponseEntity.ok(materiel);
            } else {
                List<MaterielDto> materiels = materielService.findAll();
                return ResponseEntity.ok(materiels);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
        }
    }

    @DeleteMapping("/{materielId}")
    public ResponseEntity<Void> delete(@PathVariable Integer materielId) {
        materielService.delete(materielId);
        return ResponseEntity.noContent().build();
    }

}
