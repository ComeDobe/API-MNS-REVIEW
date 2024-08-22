package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.LocalisationDto;
import com.dobe.locmns.repositories.LocalisationRepository;
import com.dobe.locmns.services.LocalisationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/localisation")
@Tag(name = "localisation")
public class LocalisationController {

    private final LocalisationService localisationService;
    private final LocalisationRepository localisationRepository;

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody LocalisationDto localisationDto) {
        return ResponseEntity.ok(localisationService.save(localisationDto));
    }

    @GetMapping("")
    public ResponseEntity <List<LocalisationDto>> findAll() {
        return ResponseEntity.ok(localisationService.findAll());
    }

    @PutMapping ("/{localisationId}")
        public ResponseEntity<Void> update(@RequestBody LocalisationDto localisationDto, @PathVariable Integer localisationId) {
        localisationService.update(localisationDto);
        return ResponseEntity.accepted().build();
    }
    @DeleteMapping("/{localisationId}")
        public ResponseEntity<Integer> delete(@PathVariable Integer localisationId) {
        localisationService.delete(localisationId);
        return ResponseEntity.accepted().build();

    }
}
