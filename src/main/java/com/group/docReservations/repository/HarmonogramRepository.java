package com.group.docReservations.repository;

import com.group.docReservations.classes.harmonogramClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HarmonogramRepository extends MongoRepository<harmonogramClass, String> {
}
