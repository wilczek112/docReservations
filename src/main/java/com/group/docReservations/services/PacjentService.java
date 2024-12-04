package com.group.docReservations.services;

import com.group.docReservations.classes.Pacjent;

import java.util.List;
import java.util.Optional;

public interface PacjentService {
    Pacjent savePacjent(Pacjent pacjent);
    List<Pacjent> findAllPacjents();
    Optional<Pacjent> findPacjentById(String id);
    Pacjent updatePacjent(String id, Pacjent updatedPacjent);
    void deletePacjent(String id);
}
