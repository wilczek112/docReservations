package com.group.docReservations.controller;

import com.group.docReservations.classes.userClass;
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
    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    public authController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) return "redirect:/panel";
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/panel";
    }

    @RequestMapping("/panel")
    public String panel(Model model) throws SocketException, UnknownHostException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        userClass userClass = userService.findUserByEmail(currentPrincipalName);
        model.addAttribute("user", userClass);
        model.addAttribute("auth", hasUserRole);
        return "panel";
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("user", new userClass());
        return "register";
    }



    @PostMapping(value = "/register/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") userClass newUser, BindingResult errors, Model model) throws Exception {
        userClass testLogin = userRepository.findByLogin(newUser.getLogin());
        userClass testEmail = userRepository.findByEmail(newUser.getEmail());

        if (testLogin != null && testLogin.getLogin() != null && !testLogin.getLogin().isEmpty()) {
            errors.rejectValue("login", null, "Istnieje Email o tym samym loginie");
        }
        if (testEmail != null && testEmail.getEmail() != null && !testEmail.getEmail().isEmpty()) {
            errors.rejectValue("email", null, "Istnieje konto o tym samym email");
        }
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", null, "Passwords do not match");
        }
        if (errors.hasErrors()) {
            return "register";
        } else {
            newUser.setRole("ROLE_USER");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userService.saveUser(newUser);
            return "redirect:/register?success";
        }
    }


    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<userClass> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
