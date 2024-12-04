package com.group.docReservations.repository;

import com.group.docReservations.classes.Harmonogram;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HarmonogramRepository extends MongoRepository<Harmonogram, String> {
}
