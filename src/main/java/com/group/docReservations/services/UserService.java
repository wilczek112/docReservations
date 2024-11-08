package com.group.docReservations.services;

import com.group.docReservations.classes.userClass;
import java.util.List;

public interface UserService {
    void saveUser(userClass user) throws Exception;
    userClass findUserByLogin(String login);
    userClass findUserByEmail(String email);
    List<userClass> findAllUsers();
}
