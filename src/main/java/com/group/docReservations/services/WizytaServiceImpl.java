package com.group.docReservations.services;

import com.group.docReservations.classes.Wizyta;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WizytaServiceImpl implements WizytaService {

    private final WizytaRepository wizytaRepository;

    @Autowired
    public WizytaServiceImpl(WizytaRepository wizytaRepository) {
        this.wizytaRepository = wizytaRepository;
    }

    @Override
    public Wizyta saveWizyta(Wizyta wizyta) throws Exception {
        if (wizyta == null) {
            throw new IllegalArgumentException("Wizyta cannot be null");
        }
        return wizytaRepository.save(wizyta);
    }

    @Override
    public Optional<Wizyta> findWizytaById(String id) {
        return wizytaRepository.findById(id);
    }

    @Override
    public List<Wizyta> findAllWizyty() {
        return wizytaRepository.findAll();
    }

    @Override
    public Wizyta updateWizyta(Wizyta updatedWizyta) throws Exception {
        if (!wizytaRepository.existsById(updatedWizyta.getId())) {
            throw new ResourceNotFoundException("Wizyta not found with id " + updatedWizyta.getId());
        }
        return wizytaRepository.save(updatedWizyta);
    }

    @Override
    public void deleteWizyta(String id) {
        if (!wizytaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Wizyta not found with id " + id);
        }
        wizytaRepository.deleteById(id);
    }
}
