package com.group.docReservations.services;

import com.group.docReservations.classes.Specjalizacja;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.SpecjalizacjaRepository;
import com.group.docReservations.services.SpecjalizacjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecjalizacjaServiceImpl implements SpecjalizacjaService {

    private final SpecjalizacjaRepository specjalizacjaRepository;

    @Autowired
    public SpecjalizacjaServiceImpl(SpecjalizacjaRepository specjalizacjaRepository) {
        this.specjalizacjaRepository = specjalizacjaRepository;
    }

    @Override
    public Specjalizacja saveSpecjalizacja(Specjalizacja specjalizacja) throws Exception {
        if (specjalizacja == null) {
            throw new IllegalArgumentException("Specjalizacja cannot be null");
        }
        return specjalizacjaRepository.save(specjalizacja);
    }

    @Override
    public Optional<Specjalizacja> findSpecjalizacjaById(String id) {
        return specjalizacjaRepository.findById(id);
    }

    @Override
    public List<Specjalizacja> findAllSpecjalizacje() {
        return specjalizacjaRepository.findAll();
    }

    @Override
    public Specjalizacja updateSpecjalizacja(Specjalizacja updatedSpecjalizacja) throws Exception {
        if (updatedSpecjalizacja.getId() == null || !specjalizacjaRepository.existsById(updatedSpecjalizacja.getId())) {
            throw new ResourceNotFoundException("Specjalizacja not found with id " + updatedSpecjalizacja.getId());
        }
        return specjalizacjaRepository.save(updatedSpecjalizacja);
    }

    @Override
    public void deleteSpecjalizacja(String id) {
        if (!specjalizacjaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Specjalizacja not found with id " + id);
        }
        specjalizacjaRepository.deleteById(id);
    }
}
