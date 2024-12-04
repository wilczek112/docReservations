package com.group.docReservations.repository;

import com.group.docReservations.classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
    User findByEmail(String email);
    //userClass findById(String id);
}
