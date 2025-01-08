package com.group.docReservations.services;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.classes.User;
import com.group.docReservations.classes.Wizyta;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WizytaServiceImpl implements WizytaService {

    private final WizytaRepository wizytaRepository;
    private final LekarzService lekarzService;
    private final UserService userService;


    @Autowired
    public WizytaServiceImpl(WizytaRepository wizytaRepository, LekarzService lekarzService, UserService userService) {
        this.wizytaRepository = wizytaRepository;
        this.lekarzService = lekarzService;
        this.userService = userService;
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
    @Override
    public List<Wizyta> findWizytyByUserId(String userId) {
        List<Wizyta> wizyty = wizytaRepository.findAll().stream()
                .filter(wizyta -> wizyta.getUser_id().equals(userId))
                .collect(Collectors.toList());

        // Populate lekarz and user details for each wizyta
        wizyty.forEach(wizyta -> {
            Optional<Lekarz> lekarzOpt = lekarzService.findLekarzById(wizyta.getLekarz_id());
            if (lekarzOpt.isPresent()) {
                Lekarz lekarz = lekarzOpt.get();
                Optional<User> userOpt = userService.findUserById(lekarz.getUserId());
                if (userOpt.isPresent()) {
                    lekarz.setUser(userOpt.get()); // Set the user details in the lekarz object
                    wizyta.setLekarz(lekarz);      // Set the populated lekarz in the wizyta
                } else {
                    throw new ResourceNotFoundException("User not found for doctor with ID " + lekarz.getUserId());
                }
            } else {
                throw new ResourceNotFoundException("Lekarz not found with ID " + wizyta.getLekarz_id());
            }
        });

        return wizyty;
    }


}
