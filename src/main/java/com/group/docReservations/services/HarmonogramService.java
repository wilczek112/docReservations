package com.group.docReservations.services;

import com.group.docReservations.classes.Harmonogram;

import java.util.List;
import java.util.Optional;

public interface HarmonogramService {

    List<Harmonogram> findAllHarmonograms();

    Optional<Harmonogram> findHarmonogramById(String id);

    Harmonogram saveHarmonogram(Harmonogram harmonogram);

    Harmonogram updateHarmonogram(Harmonogram harmonogram);

    void deleteHarmonogram(String id);
}
