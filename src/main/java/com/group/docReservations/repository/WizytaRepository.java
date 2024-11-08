package com.group.docReservations.repository;

import com.group.docReservations.classes.wizytaClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WizytaRepository extends MongoRepository<wizytaClass, String> {
}
