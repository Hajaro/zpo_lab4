package laby.lab4_spring.service;

import laby.lab4_spring.model.Zadanie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ZadanieService {
    Optional<Zadanie> getZadanie(Integer zadanieId, Integer projektId);
    Zadanie setZadanie(Zadanie zadanie, Integer projektId);
    Page<Zadanie> getZadaniaProjektu(Integer projektId, Pageable pageable);
    void deleteZadanie(Integer zadanieId, Integer projektId);
}
