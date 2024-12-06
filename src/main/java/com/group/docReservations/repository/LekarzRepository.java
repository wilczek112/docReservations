package com.group.docReservations.repository;

import com.group.docReservations.classes.Lekarz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LekarzRepository extends MongoRepository<Lekarz, String> {
}
