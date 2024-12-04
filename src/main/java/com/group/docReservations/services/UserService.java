package com.group.docReservations.services;

import com.group.docReservations.classes.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user) throws Exception;

    User findUserByLogin(String login);

    User findUserByEmail(String email);

    Optional<User> findUserById(String id);

    User updateUser(User updatedUser);

    void deleteUser(String id);

    List<User> findAllUsers();
}
