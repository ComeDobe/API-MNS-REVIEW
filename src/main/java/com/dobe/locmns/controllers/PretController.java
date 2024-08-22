package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.PretDto;
import com.dobe.locmns.models.Pret;
import com.dobe.locmns.repositories.PretRepository;
import com.dobe.locmns.services.PretService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pret")
@Tag(name = "pret")
public class PretController {

    private final PretService pretService;
    private final PretRepository pretRepository;

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody PretDto pretDto) {
        return ResponseEntity.ok(pretService.save(pretDto));
    }
    @GetMapping("")
    public ResponseEntity<List<PretDto>> findAll() {
        return ResponseEntity.ok(pretService.findAll());
    }
    @GetMapping("/{pretId}")
    public ResponseEntity<PretDto> findById(@PathVariable Integer pretId) {
        return ResponseEntity.ok(pretService.findById(pretId));

    }
    @DeleteMapping("/{pretId}")
    public ResponseEntity<Void> delete(@PathVariable Integer pretId) {
        pretService.delete(pretId);
        return ResponseEntity.accepted().build();

    }
}

