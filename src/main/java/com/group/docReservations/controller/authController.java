package com.group.docReservations.controller;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.classes.User;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.repository.UserRepository;
import com.group.docReservations.services.LekarzService;
import com.group.docReservations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class authController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LekarzService lekarzService;

    public authController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LekarzService lekarzService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lekarzService = lekarzService;
    }

    @GetMapping("/index")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/panel";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/panel";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value = "/register/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User newUser, BindingResult errors, Model model) throws Exception {
        User testLogin = userRepository.findByLogin(newUser.getLogin());
        User testEmail = userRepository.findByEmail(newUser.getEmail());
        if (testLogin != null) {
            errors.rejectValue("login", null, "A user with this login already exists.");
        }
        if (testEmail != null) {
            errors.rejectValue("email", null, "A user with this email already exists.");
        }
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Passwords do not match.");
        }
        if (errors.hasErrors()) {
            return "register";
        } else {
            newUser.setRole("ROLE_USER");
            // Encrypt the password before saving
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userService.saveUser(newUser);
            return "redirect:/register?success";
        }
    }

}
