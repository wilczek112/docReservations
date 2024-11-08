package com.group.docReservations.services;

import com.group.docReservations.classes.userClass;
import com.group.docReservations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(userClass user) throws Exception {
        // Here you can add any business logic like password hashing before saving
        userRepository.save(user);
    }

    @Override
    public userClass findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public userClass findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<userClass> findAllUsers() {
        return userRepository.findAll();
    }
}
