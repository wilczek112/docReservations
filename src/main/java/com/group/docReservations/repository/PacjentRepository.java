package com.group.docReservations.repository;

import com.group.docReservations.classes.Pacjent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacjentRepository extends MongoRepository<Pacjent, String> {
    // You can add custom query methods if needed
}
