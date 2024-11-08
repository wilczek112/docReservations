package com.group.docReservations.repository;

import com.group.docReservations.classes.userClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<userClass, String> {
    userClass findByLogin(String login);
    userClass findByEmail(String email);
    //userClass findById(String id);
}
