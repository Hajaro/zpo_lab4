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



}
