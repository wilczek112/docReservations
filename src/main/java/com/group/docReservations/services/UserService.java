package com.group.docReservations.services;

import com.group.docReservations.classes.User;

import java.util.List;

public interface UserService {
    void saveUser(User user) throws Exception;
    User findUserByLogin(String login);
    User findUserByEmail(String email);
    List<User> findAllUsers();
}
