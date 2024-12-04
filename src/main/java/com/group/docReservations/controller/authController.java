package com.group.docReservations.controller;

import com.group.docReservations.classes.User;
import com.group.docReservations.repository.UserRepository;
import com.group.docReservations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class authController {

    private final UserRepository userRepository;
    private final UserService userService;

    public authController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/panel";
        }
        return "index"; // Show the login page
    }

    @RequestMapping("/panel")
    public String panel(Model model) throws SocketException, UnknownHostException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        User user = userService.findUserByEmail(currentPrincipalName);
        model.addAttribute("user", user);
        model.addAttribute("auth", hasAdminRole);
        return "panel";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User newUser, BindingResult errors, Model model) {
        User testLogin = userRepository.findByLogin(newUser.getLogin());
        User testEmail = userRepository.findByEmail(newUser.getEmail());

        if (testLogin != null) {
            errors.rejectValue("login", null, "Istnieje użytkownik o tym samym loginie.");
        }
        if (testEmail != null) {
            errors.rejectValue("email", null, "Istnieje użytkownik o tym samym emailu.");
        }
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Hasła nie są zgodne.");
        }

        if (errors.hasErrors()) {
            return "register";
        }

        newUser.setRole("ROLE_USER");
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        try {
            userService.saveUser(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/register?success";
    }
}

