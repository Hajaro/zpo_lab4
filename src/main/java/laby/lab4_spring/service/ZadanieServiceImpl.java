package laby.lab4_spring.service;

import laby.lab4_spring.model.Zadanie;
import laby.lab4_spring.repository.ProjektRepository;
import laby.lab4_spring.repository.ZadanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZadanieServiceImpl implements ZadanieService {
    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;


    public ZadanieServiceImpl(ZadanieRepository zadanieRepository) {this.zadanieRepository = zadanieRepository;}

    public ZadanieServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepository) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepository;
    }

    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId, Integer projektId) {
        return zadanieRepository.findZadaniaProjektu(projektId)
                .stream()
                .filter(z -> z.getZadanieId().equals(zadanieId))
                .findFirst();
    }
    @Override
    public Zadanie setZadanie(Zadanie zadanie, Integer projektId) {
        if (projektRepository.existsById(projektId)) {
            zadanie.setProjekt(projektRepository.findById(projektId).orElseThrow());
            return zadanieRepository.save(zadanie);
        } else {
            throw new IllegalArgumentException("Projekt o podanym ID nie istnieje");
        }
    }
    @Override
    public Page<Zadanie> getZadaniaProjektu(Integer projektId, Pageable pageable) {
        return zadanieRepository.findZadaniaProjektu(projektId, pageable);
    }
    @Override
    public void deleteZadanie(Integer zadanieId, Integer projektId) {
        Optional<Zadanie> zadanie = getZadanie(zadanieId, projektId);
        if (zadanie.isPresent()) {
            zadanieRepository.delete(zadanie.get());
        } else {
            throw new IllegalArgumentException("Zadanie o podanym ID nie istnieje w projekcie o podanym ID");
        }
    }

}
