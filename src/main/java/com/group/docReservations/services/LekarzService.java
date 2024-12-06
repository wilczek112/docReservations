package com.group.docReservations.services;

import com.group.docReservations.classes.Lekarz;

import java.util.List;
import java.util.Optional;

public interface LekarzService {

    Lekarz saveLekarz(Lekarz lekarz) throws Exception;

    Optional<Lekarz> findLekarzById(String id);

    List<Lekarz> findAllLekarze();

    Lekarz updateLekarz(Lekarz updatedLekarz) throws Exception;

    void deleteLekarz(String id);
}
