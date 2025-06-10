package laby.lab4_spring.service;

import jakarta.transaction.Transactional;
import laby.lab4_spring.model.Projekt;
import laby.lab4_spring.model.Zadanie;
import laby.lab4_spring.repository.ProjektRepository;
import laby.lab4_spring.repository.ZadanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProjektServiceImpl implements ProjektService {
    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;

    public ProjektServiceImpl(ProjektRepository projektRepository) {
        this.projektRepository = projektRepository;
    }
    @Autowired // w tej wersji konstruktora Spring wstrzyknie dwa repozytoria
    public ProjektServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepo) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepo;
    }
    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }
    @Override
    public Projekt setProjekt(Projekt projekt) {
        if (projekt.getProjektId() == null) {
            projekt.setCreatedDate(LocalDateTime.now());
        } else {
            projekt.setLastModifiedDate(LocalDateTime.now());
        }
        return projektRepository.save(projekt);
    }
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {
        for (Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)) {
            zadanieRepository.delete(zadanie);
        }
        projektRepository.deleteById(projektId);
    }
    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektRepository.findAll(pageable);
    }
    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        return projektRepository.findByNazwaContainingIgnoreCase(nazwa, pageable);
    }
}