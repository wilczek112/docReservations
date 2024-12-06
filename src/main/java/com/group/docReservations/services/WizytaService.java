package com.group.docReservations.services;

import com.group.docReservations.classes.Wizyta;

import java.util.List;
import java.util.Optional;

public interface WizytaService {
    Wizyta saveWizyta(Wizyta wizyta) throws Exception;

    Optional<Wizyta> findWizytaById(String id);

    List<Wizyta> findAllWizyty();

    Wizyta updateWizyta(Wizyta updatedWizyta) throws Exception;

    void deleteWizyta(String id);

}
