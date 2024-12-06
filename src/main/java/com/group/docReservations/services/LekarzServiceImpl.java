package com.group.docReservations.services;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.LekarzRepository;
import com.group.docReservations.services.LekarzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LekarzServiceImpl implements LekarzService {

    private final LekarzRepository lekarzRepository;

    @Autowired
    public LekarzServiceImpl(LekarzRepository lekarzRepository) {
        this.lekarzRepository = lekarzRepository;
    }

    @Override
    public Lekarz saveLekarz(Lekarz lekarz) throws Exception {
        if (lekarz == null) {
            throw new IllegalArgumentException("Lekarz cannot be null");
        }
        return lekarzRepository.save(lekarz);
    }

    @Override
    public Optional<Lekarz> findLekarzById(String id) {
        return lekarzRepository.findById(id);
    }

    @Override
    public List<Lekarz> findAllLekarze() {
        return lekarzRepository.findAll();
    }

    @Override
    public Lekarz updateLekarz(Lekarz updatedLekarz) throws Exception {
        if (!lekarzRepository.existsById(updatedLekarz.getId())) {
            throw new ResourceNotFoundException("Lekarz not found with id " + updatedLekarz.getId());
        }
        return lekarzRepository.save(updatedLekarz);
    }

    @Override
    public void deleteLekarz(String id) {
        if (!lekarzRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lekarz not found with id " + id);
        }
        lekarzRepository.deleteById(id);
    }
}
