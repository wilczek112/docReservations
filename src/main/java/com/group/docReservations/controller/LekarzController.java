package com.group.docReservations.controller;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.LekarzService;
import com.group.docReservations.services.SpecjalizacjaService;
import com.group.docReservations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lekarze")
public class LekarzController {

    private final LekarzService lekarzService;
    private final UserService userService;
    private final SpecjalizacjaService specjalizacjaService;

    @Autowired
    public LekarzController(LekarzService lekarzService, UserService userService, SpecjalizacjaService specjalizacjaService) {
        this.lekarzService = lekarzService;
        this.userService = userService;
        this.specjalizacjaService = specjalizacjaService;
    }

    @GetMapping
    public String listLekarze(Model model, Authentication authentication) {
        List<Lekarz> lekarze = lekarzService.findAllLekarze();
        model.addAttribute("title", "Lista lekarzy");
        model.addAttribute("headers", Arrays.asList("User ID", "Specjalizacja ID"));
        model.addAttribute("fields", Arrays.asList("userId", "specjalizacjaId"));
        model.addAttribute("entities", lekarze);
        model.addAttribute("editUrl", "/lekarze/edit");
        model.addAttribute("deleteUrl", "/lekarze/delete");
        model.addAttribute("addUrl", "/lekarze/add");
        return "base-crud";
    }

    @GetMapping("/add")
    public String showAddLekarzForm(Model model) {
        model.addAttribute("title", "Dodaj Lekarza");
        model.addAttribute("entity", new Lekarz());
        model.addAttribute("actionUrl", "/lekarze/add");

        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "userId", "label", "Użytkownik", "type", "dropdown", "placeholder", "Wybierz użytkownika", "options", userService.findAllUsers()
                        .stream()
                        .map(user -> Map.of("value", user.getId(), "label", user.getFirstName() + " " + user.getLastName()))
                        .toList()),
                Map.of("name", "specjalizacjaId", "label", "Specjalizacja", "type", "dropdown", "placeholder", "Wybierz specjalizację", "options", specjalizacjaService.findAllSpecjalizacje()
                        .stream()
                        .map(spec -> Map.of("value", spec.getId(), "label", spec.getNazwa()))
                        .toList())
        ));

        return "base-form";
    }

    @PostMapping("/add")
    public String addLekarz(@Valid @ModelAttribute("entity") Lekarz lekarz, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Dodaj lekarza");
            model.addAttribute("actionUrl", "/lekarze/add");
            return "base-form";
        }
        try {
            lekarz.setId(null);
            lekarzService.saveLekarz(lekarz);
            model.addAttribute("message", "Lekarz został dodany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateDropdowns(model);
            model.addAttribute("message", "Nie udało się dodać lekarza.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/lekarze";
    }

    @GetMapping("/edit/{id}")
    public String showEditLekarzForm(@PathVariable String id, Model model) {
        Lekarz lekarz = lekarzService.findLekarzById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lekarz not found with id " + id));
        model.addAttribute("title", "Edytuj lekarza");
        model.addAttribute("entity", lekarz);
        model.addAttribute("actionUrl", "/lekarze/edit/" + id);

        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "userId", "label", "Użytkownik", "type", "dropdown", "placeholder", "Wybierz użytkownika", "options", userService.findAllUsers()
                        .stream()
                        .map(user -> Map.of("value", user.getId(), "label", user.getFirstName() + " " + user.getLastName()))
                        .toList()),
                Map.of("name", "specjalizacjaId", "label", "Specjalizacja", "type", "dropdown", "placeholder", "Wybierz specjalizację", "options", specjalizacjaService.findAllSpecjalizacje()
                        .stream()
                        .map(spec -> Map.of("value", spec.getId(), "label", spec.getNazwa()))
                        .toList())
        ));
        return "base-form";
    }

    @PostMapping("/edit/{id}")
    public String editLekarz(@PathVariable String id, @Valid @ModelAttribute("entity") Lekarz lekarz, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Edytuj lekarza");
            model.addAttribute("actionUrl", "/lekarze/edit/" + id);
            return "base-form";
        }
        try {
            lekarz.setId(id);
            lekarzService.updateLekarz(lekarz);
            model.addAttribute("message", "Lekarz zaktualizowany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateDropdowns(model);
            model.addAttribute("message", "Nie udało się zaktualizować lekarza.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/lekarze";
    }

    @GetMapping("/delete/{id}")
    public String deleteLekarz(@PathVariable String id) {
        lekarzService.deleteLekarz(id);
        return "redirect:/lekarze";
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("specjalizacje", specjalizacjaService.findAllSpecjalizacje());
    }
}
