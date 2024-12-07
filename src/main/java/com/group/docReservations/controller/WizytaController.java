package com.group.docReservations.controller;

import com.group.docReservations.classes.Wizyta;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.LekarzService;
import com.group.docReservations.services.UserService;
import com.group.docReservations.services.WizytaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wizyty")
public class WizytaController {

    private final WizytaService wizytaService;
    private final LekarzService lekarzService;
    private final UserService userService;

    @Autowired
    public WizytaController(WizytaService wizytaService, LekarzService lekarzService, UserService userService) {
        this.wizytaService = wizytaService;
        this.lekarzService = lekarzService;
        this.userService = userService;
    }

    @GetMapping
    public String listWizyty(Model model) {
        List<Wizyta> wizyty = wizytaService.findAllWizyty();
        model.addAttribute("title", "Lista Wizyt");
        model.addAttribute("headers", Arrays.asList("Pacjent ID", "Lekarz ID", "Początek Wizyty", "Koniec Wizyty", "Status"));
        model.addAttribute("fields", Arrays.asList("user_id", "lekarz_id", "start_time", "end_time", "status"));
        model.addAttribute("entities", wizyty);
        model.addAttribute("editUrl", "/wizyty/edit");
        model.addAttribute("deleteUrl", "/wizyty/delete");
        model.addAttribute("addUrl", "/wizyty/add");
        return "base-crud";
    }

    @GetMapping("/add")
    public String showAddWizytaForm(Model model) {
        model.addAttribute("title", "Dodaj Wizytę");
        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "user_id", "label", "Pacjent", "type", "dropdown", "placeholder", "Wybierz pacjenta", "options", userService.findAllUsers()
                        .stream()
                        .map(user -> Map.of("value", user.getId(), "label", user.getFirstName() + " " + user.getLastName()))
                        .toList()),
                Map.of("name", "lekarz_id", "label", "Lekarz", "type", "dropdown", "placeholder", "Wybierz lekarza", "options", lekarzService.findAllLekarze()
                        .stream()
                        .map(lekarz -> Map.of("value", lekarz.getId(), "label", lekarz.getUserId()))
                        .toList()),
                Map.of("name", "start_time", "label", "Początek Wizyty", "type", "datetime-local"),
                Map.of("name", "end_time", "label", "Koniec Wizyty", "type", "datetime-local"),
                Map.of("name", "status", "label", "Status", "type", "text", "placeholder", "Wpisz status wizyty")
        ));
        model.addAttribute("entity", new Wizyta());
        model.addAttribute("actionUrl", "/wizyty/add");
        return "base-form";
    }

    @PostMapping("/add")
    public String addWizyta(@Valid @ModelAttribute("entity") Wizyta wizyta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Dodaj Wizytę");
            model.addAttribute("actionUrl", "/wizyty/add");
            return "base-form";
        }

        LocalDateTime startDateTime = wizyta.getStart_time();
        LocalDateTime endDateTime = wizyta.getEnd_time();

        if (endDateTime.isBefore(startDateTime)) {
            result.rejectValue("end_time", "error.entity", "Koniec wizyty musi być później niż początek.");
            populateDropdowns(model);
            model.addAttribute("title", "Dodaj Wizytę");
            model.addAttribute("actionUrl", "/wizyty/add");
            return "base-form";
        }

        try {
            wizyta.setId(null);
            wizytaService.saveWizyta(wizyta);
            model.addAttribute("message", "Wizyta została dodana pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateDropdowns(model);
            model.addAttribute("message", "Nie udało się dodać wizyty.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/wizyty";
    }

    @GetMapping("/edit/{id}")
    public String showEditWizytaForm(@PathVariable String id, Model model) {
        Wizyta wizyta = wizytaService.findWizytaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wizyta not found with id " + id));

        model.addAttribute("title", "Edytuj Wizytę");
        model.addAttribute("entity", wizyta);
        model.addAttribute("actionUrl", "/wizyty/edit/" + id);

        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "user_id", "label", "Pacjent", "type", "dropdown", "placeholder", "Wybierz pacjenta", "options", userService.findAllUsers()
                        .stream()
                        .map(user -> Map.of("value", user.getId(), "label", user.getFirstName() + " " + user.getLastName()))
                        .toList()),
                Map.of("name", "lekarz_id", "label", "Lekarz", "type", "dropdown", "placeholder", "Wybierz lekarza", "options", lekarzService.findAllLekarze()
                        .stream()
                        .map(lekarz -> Map.of("value", lekarz.getId(), "label", lekarz.getUserId()))
                        .toList()),
                Map.of("name", "start_time", "label", "Początek Wizyty", "type", "datetime-local"),
                Map.of("name", "end_time", "label", "Koniec Wizyty", "type", "datetime-local"),
                Map.of("name", "status", "label", "Status", "type", "text", "placeholder", "Wpisz status wizyty")
        ));

        return "base-form";
    }

    @PostMapping("/edit/{id}")
    public String editWizyta(@PathVariable String id, @Valid @ModelAttribute("entity") Wizyta wizyta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Edytuj Wizytę");
            model.addAttribute("actionUrl", "/wizyty/edit/" + id);
            return "base-form";
        }

        LocalDateTime startDateTime = wizyta.getStart_time();
        LocalDateTime endDateTime = wizyta.getEnd_time();

        if (endDateTime.isBefore(startDateTime)) {
            result.rejectValue("end_time", "error.entity", "Koniec wizyty musi być później niż początek.");
            populateDropdowns(model);
            model.addAttribute("title", "Edytuj Wizytę");
            model.addAttribute("actionUrl", "/wizyty/edit/" + id);
            return "base-form";
        }

        try {
            wizyta.setId(id);
            wizytaService.updateWizyta(wizyta);
            model.addAttribute("message", "Wizyta zaktualizowana pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateDropdowns(model);
            model.addAttribute("message", "Nie udało się zaktualizować wizyty.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/wizyty";
    }

    @GetMapping("/delete/{id}")
    public String deleteWizyta(@PathVariable String id) {
        wizytaService.deleteWizyta(id);
        return "redirect:/wizyty";
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("pacjenci", userService.findAllUsers());
        model.addAttribute("lekarze", lekarzService.findAllLekarze());
    }
}
