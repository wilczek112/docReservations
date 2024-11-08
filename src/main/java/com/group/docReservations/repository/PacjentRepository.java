package com.group.docReservations.repository;

import com.group.docReservations.classes.pacjentClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacjentRepository extends MongoRepository<pacjentClass, String> {
}
