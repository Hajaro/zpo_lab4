package laby.lab4_spring.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import laby.lab4_spring.model.Projekt;
import laby.lab4_spring.model.Zadanie;
import laby.lab4_spring.service.ProjektService;
import laby.lab4_spring.service.ZadanieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@RestController
@RequestMapping("/api")
@Tag (name = "Zadanie")
public class ZadanieRestController {
    private ZadanieService zadanieService;
    private ProjektService projektService;

    public ZadanieRestController(ZadanieService zadanieService, ProjektService projektService) {
        this.zadanieService = zadanieService;
        this.projektService = projektService;
    }

    @GetMapping("/projekty/{projektId}/zadania")
    public ResponseEntity<Page<Zadanie>> getZadaniaProjektu(@PathVariable Integer projektId, Pageable pageable) {
        if (!projektService.getProjekt(projektId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Page<Zadanie> zadania = zadanieService.getZadaniaProjektu(projektId, pageable);
        return ResponseEntity.ok(zadania);
    }

    @GetMapping("/projekty/{projektId}/zadania/{zadanieId}")
    public ResponseEntity<Zadanie> getZadanie(@PathVariable Integer projektId, @PathVariable Integer zadanieId) {
        if (!projektService.getProjekt(projektId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return zadanieService.getZadanie(zadanieId, projektId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/projekty/{projektId}/zadania")
    public ResponseEntity<Zadanie> setZadanie(@PathVariable Integer projektId, @Valid @RequestBody Zadanie zadanie) {
        if (!projektService.getProjekt(projektId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        zadanie = zadanieService.setZadanie(zadanie, projektId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zadanieId}")
                .buildAndExpand(zadanie.getZadanieId())
                .toUri();
        return ResponseEntity.created(location).body(zadanie);
    }
    @PutMapping("/projekty/{projektId}/zadania/{zadanieId}")
    public ResponseEntity<Zadanie> updateZadanie(@PathVariable Integer projektId, @PathVariable Integer zadanieId, @Valid @RequestBody Zadanie zadanie) {
        if (!projektService.getProjekt(projektId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        zadanie.setZadanieId(zadanieId);
        zadanie = zadanieService.setZadanie(zadanie, projektId);
        return ResponseEntity.ok(zadanie);
    }
    @DeleteMapping("/projekty/{projektId}/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer projektId, @PathVariable Integer zadanieId) {
        if (!projektService.getProjekt(projektId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        zadanieService.deleteZadanie(zadanieId, projektId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
