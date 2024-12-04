package com.group.docReservations.services;

import com.group.docReservations.classes.Pacjent;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.PacjentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacjentServiceImpl implements PacjentService {

    @Autowired
    private PacjentRepository pacjentRepository;

    @Override
    public Pacjent savePacjent(Pacjent pacjent) {
        return pacjentRepository.save(pacjent);
    }

    @Override
    public List<Pacjent> findAllPacjents() {
        return pacjentRepository.findAll();
    }

    @Override
    public Optional<Pacjent> findPacjentById(String id) {
        return pacjentRepository.findById(id);
    }

    @Override
    public Pacjent updatePacjent(String id, Pacjent updatedPacjent) {
        Optional<Pacjent> existingPacjentOpt = pacjentRepository.findById(id);
        if (existingPacjentOpt.isPresent()) {
            Pacjent existingPacjent = existingPacjentOpt.get();
            // Update fields
            existingPacjent.setImie(updatedPacjent.getImie());
            existingPacjent.setNazwisko(updatedPacjent.getNazwisko());
            existingPacjent.setDataUrodzenia(updatedPacjent.getDataUrodzenia());
            existingPacjent.setTelefon(updatedPacjent.getTelefon());
            existingPacjent.setEmail(updatedPacjent.getEmail());
            // Save updated pacjent
            return pacjentRepository.save(existingPacjent);
        } else throw new ResourceNotFoundException("Pacjent not found with id " + id);
    }

    @Override
    public void deletePacjent(String id) {
        if (pacjentRepository.existsById(id)) {
            pacjentRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Pacjent not found with id " + id);
    }
}
