package com.group.docReservations.repository;

import com.group.docReservations.classes.Specjalizacja;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecjalizacjaRepository extends MongoRepository<Specjalizacja, String> {
}
