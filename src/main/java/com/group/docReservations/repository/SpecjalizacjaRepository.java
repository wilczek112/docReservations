package com.group.docReservations.repository;

import com.group.docReservations.classes.specjalizacjaClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpecjalizacjaRepository extends MongoRepository<specjalizacjaClass, String> {
}
