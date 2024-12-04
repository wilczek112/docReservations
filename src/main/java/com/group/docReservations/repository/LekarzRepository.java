package com.group.docReservations.repository;

import com.group.docReservations.classes.Lekarz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LekarzRepository extends MongoRepository<Lekarz, String> {
}
