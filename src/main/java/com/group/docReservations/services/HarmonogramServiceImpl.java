package com.group.docReservations.services;

import com.group.docReservations.classes.Harmonogram;
import com.group.docReservations.repository.HarmonogramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HarmonogramServiceImpl implements HarmonogramService {

    private final HarmonogramRepository harmonogramRepository;

    @Autowired
    public HarmonogramServiceImpl(HarmonogramRepository harmonogramRepository) {
        this.harmonogramRepository = harmonogramRepository;
    }

    @Override
    public List<Harmonogram> findAllHarmonograms() {
        return harmonogramRepository.findAll();
    }

    @Override
    public Optional<Harmonogram> findHarmonogramById(String id) {
        return harmonogramRepository.findById(id);
    }

    @Override
    public Harmonogram saveHarmonogram(Harmonogram harmonogram) {
        return harmonogramRepository.save(harmonogram);
    }

    @Override
    public Harmonogram updateHarmonogram(Harmonogram harmonogram) {
        if (!harmonogramRepository.existsById(harmonogram.getId())) {
            throw new RuntimeException("Harmonogram not found with id " + harmonogram.getId());
        }
        return harmonogramRepository.save(harmonogram);
    }

    @Override
    public void deleteHarmonogram(String id) {
        if (!harmonogramRepository.existsById(id)) {
            throw new RuntimeException("Harmonogram not found with id " + id);
        }
        harmonogramRepository.deleteById(id);
    }
}
