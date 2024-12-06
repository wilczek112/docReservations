package com.group.docReservations.services;

import com.group.docReservations.classes.User;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Injected encoder
    }

    @Override
    public User saveUser(User user) throws Exception {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password before saving
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + updatedUser.getId()));

        // Update only non-null fields
        Optional.ofNullable(updatedUser.getLogin()).ifPresent(existingUser::setLogin);
        Optional.ofNullable(updatedUser.getFirstName()).ifPresent(existingUser::setFirstName);
        Optional.ofNullable(updatedUser.getLastName()).ifPresent(existingUser::setLastName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(updatedUser.getPhone()).ifPresent(existingUser::setPhone);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(password ->
                existingUser.setPassword(passwordEncoder.encode(password))); // Encrypt new password
        Optional.ofNullable(updatedUser.getRole()).ifPresent(existingUser::setRole);

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
