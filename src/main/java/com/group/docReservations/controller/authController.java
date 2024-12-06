package com.group.docReservations.controller;

import com.group.docReservations.classes.User;
import com.group.docReservations.repository.UserRepository;
import com.group.docReservations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class authController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public authController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/panel")
    public String panel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        User user = userService.findUserByEmail(currentPrincipalName);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", isAdmin);
        return "panel";
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
