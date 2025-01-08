package com.group.docReservations.controller;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.classes.User;
import com.group.docReservations.classes.Wizyta;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.LekarzService;
import com.group.docReservations.services.UserService;
import com.group.docReservations.services.WizytaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PanelController {
    private final WizytaService wizytaService;
    private final LekarzService lekarzService;
    private final UserService userService;

    @Autowired
    public PanelController(WizytaService wizytaService, LekarzService lekarzService, UserService userService) {
        this.wizytaService = wizytaService;
        this.lekarzService = lekarzService;
        this.userService = userService;
    }

    @GetMapping("/panel")
    public String panel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email " + currentPrincipalName));

        // Fetch and enrich doctors with user details
        List<Lekarz> lekarze = lekarzService.findAllLekarze();
        List<Map<String, String>> enrichedDoctors = lekarze.stream()
                .map(lekarz -> {
                    User doctorUser = userService.findUserById(lekarz.getUserId())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found for doctor with ID " + lekarz.getUserId()));
                    return Map.of(
                            "id", lekarz.getId(),
                            "userFirstName", doctorUser.getFirstName(),
                            "userLastName", doctorUser.getLastName()
                    );
                }).collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("doctors", enrichedDoctors);
        return "panel";
    }

    @GetMapping("/admin")
    public String panelAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findUserByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email " + currentPrincipalName));
        model.addAttribute("user", user);
        return "panelAdmin";
    }

    @GetMapping("/panel/mojeWizyty")
    public String mojeWizyty(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.findUserByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email " + currentPrincipalName));

        List<Wizyta> wizyty = wizytaService.findWizytyByUserId(user.getId());

        model.addAttribute("wizyty", wizyty);
        return "userWizyty";
    }
}

