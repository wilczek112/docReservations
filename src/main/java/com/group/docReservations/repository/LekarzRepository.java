package com.group.docReservations.repository;

import com.group.docReservations.classes.lekarzClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LekarzRepository extends MongoRepository<lekarzClass, String> {
}
