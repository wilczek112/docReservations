package com.group.docReservations.controller;

import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.classes.Specjalizacja;
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
        populateDropdowns(model);
        model.addAttribute("title", "Dodaj lekarza");
        model.addAttribute("entity", new Lekarz());
        model.addAttribute("actionUrl", "/lekarze/add");
        return "lekarz-form";
    }

    @PostMapping("/add")
    public String addLekarz(@Valid @ModelAttribute("entity") Lekarz lekarz, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Dodaj lekarza");
            model.addAttribute("actionUrl", "/lekarze/add");
            return "lekarz-form";
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
            return "lekarz-form";
        }
        return "redirect:/lekarze";
    }

    @GetMapping("/edit/{id}")
    public String showEditLekarzForm(@PathVariable String id, Model model) {
        Lekarz lekarz = lekarzService.findLekarzById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lekarz not found with id " + id));
        populateDropdowns(model);
        model.addAttribute("title", "Edytuj lekarza");
        model.addAttribute("entity", lekarz);
        model.addAttribute("actionUrl", "/lekarze/edit/" + id);
        return "lekarz-form";
    }

    @PostMapping("/edit/{id}")
    public String editLekarz(@PathVariable String id, @Valid @ModelAttribute("entity") Lekarz lekarz, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateDropdowns(model);
            model.addAttribute("title", "Edytuj lekarza");
            model.addAttribute("actionUrl", "/lekarze/edit/" + id);
            return "lekarz-form";
        }
        try {
            // Set the ID for the existing Lekarz object
            lekarz.setId(id);

            // Debugging: Print the userId and specjalizacjaId
            System.out.println("Updating Lekarz ID: " + id);
            System.out.println("New User ID: " + lekarz.getUserId());
            System.out.println("New Specjalizacja ID: " + lekarz.getSpecjalizacjaId());

            // Update the Lekarz object in the database
            lekarzService.updateLekarz(lekarz);
        } catch (Exception e) {
            populateDropdowns(model);
            model.addAttribute("message", "Nie udało się zaktualizować lekarza.");
            model.addAttribute("messageType", "error");
            return "lekarz-form";
        }
        return "redirect:/lekarze";
    }


    @GetMapping("/delete/{id}")
    public String deleteLekarz(@PathVariable String id) {
        lekarzService.deleteLekarz(id);
        return "redirect:/lekarze";
    }

    private void populateDropdowns(Model model) {
        // Populate dropdowns with users and specializations
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("specjalizacje", specjalizacjaService.findAllSpecjalizacje());
    }
}
