package com.dobe.locmns.controllers;


import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.ReservationService;
import com.dobe.locmns.services.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public ResponseEntity<List<MaterielDto>> findAll() {
        return ResponseEntity.ok(materielService.findAll());
    }
    @PutMapping("")
    public ResponseEntity<Integer> update(@RequestBody MaterielDto materielDto) {
        return ResponseEntity.ok(materielService.update(materielDto));

    }
    @GetMapping("/{materielId}")
    public ResponseEntity<MaterielDto> findById(@PathVariable Integer materielId) {
        return ResponseEntity.ok(materielService.findById(materielId));
    }

}
