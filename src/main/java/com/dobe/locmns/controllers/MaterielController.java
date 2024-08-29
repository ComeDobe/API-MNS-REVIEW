package com.dobe.locmns.controllers;


import com.dobe.locmns.dto.MaterielDto;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/materiel")
@Tag(name = "materiel")
public class MaterielController {

    private final MaterielService materielService;
    private final ReservationService reservationService;

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
