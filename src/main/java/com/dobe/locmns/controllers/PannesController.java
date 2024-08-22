package com.dobe.locmns.controllers;

import com.dobe.locmns.dto.PannesDto;
import com.dobe.locmns.services.MaterielService;
import com.dobe.locmns.services.PannesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pannes")
@Tag(name = "panne")
public class PannesController {
    private final PannesService pannesService;
    private final MaterielService materielService;

    @PostMapping("")
    public ResponseEntity<Integer> save(@RequestBody PannesDto pannesDto) {
        return ResponseEntity.ok(pannesService.save(pannesDto));
    }
    @GetMapping("")
    public ResponseEntity<List<PannesDto>> findAll() {
        return ResponseEntity.ok(pannesService.findAll());

    }
    @PutMapping("")
    public ResponseEntity<Integer> update(@RequestBody PannesDto pannesDto) {
        return ResponseEntity.ok(pannesService.update(pannesDto, localisationId));

    }
@DeleteMapping("/{panneId}")
    public ResponseEntity<Void> delete(@PathVariable Integer panneId) {
        pannesService.delete(panneId);
        return ResponseEntity.accepted().build();

    }
}
