package com.group.docReservations.repository;

import com.group.docReservations.classes.Wizyta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WizytaRepository extends MongoRepository<Wizyta, String> {
}
