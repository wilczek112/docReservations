package com.group.docReservations.services;

import com.group.docReservations.classes.Specjalizacja;

import java.util.List;
import java.util.Optional;

public interface SpecjalizacjaService {

    Specjalizacja saveSpecjalizacja(Specjalizacja specjalizacja) throws Exception;

    Optional<Specjalizacja> findSpecjalizacjaById(String id);

    List<Specjalizacja> findAllSpecjalizacje();

    Specjalizacja updateSpecjalizacja(Specjalizacja updatedSpecjalizacja) throws Exception;

    void deleteSpecjalizacja(String id);
}
