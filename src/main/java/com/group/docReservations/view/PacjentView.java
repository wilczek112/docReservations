package com.group.docReservations.view;

import com.group.docReservations.classes.Pacjent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/pacjenci")
public class PacjentView {

    @Value("${server}")
    private String serverUrl;

    private final RestTemplate restTemplate;
    private final String apiUrl = "http://localhost:8080/api/pacjenci";

    @Autowired
    public PacjentView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        System.out.println("Server URL: " + serverUrl); // Add this to debug
    }

    @GetMapping
    public String listPacjents(Model model) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

            // Check for HTML content (e.g., login page)
            if (response.getBody().contains("<html")) {
                model.addAttribute("message", "Unauthorized access or session expired. Please log in.");
                model.addAttribute("messageType", "error");
                return "index"; // Redirect to login page
            }

            // Parse JSON response
            List<Pacjent> pacjenci = Arrays.asList(restTemplate.getForObject(apiUrl, Pacjent[].class));
            model.addAttribute("entities", pacjenci);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error loading patients.");
            model.addAttribute("messageType", "error");
        }
        return "base-crud";
    }



    @GetMapping("/add")
    public String showAddPacjentForm(Model model) {
        model.addAttribute("title", "Dodaj Pacjenta");
        model.addAttribute("fields", List.of("imie", "nazwisko", "dataUrodzenia", "telefon", "email"));
        model.addAttribute("entity", new Pacjent());
        model.addAttribute("actionUrl", "/pacjenci/add");
        return "base-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditPacjentForm(@PathVariable String id, Model model) {
        Pacjent pacjent = restTemplate.getForObject(apiUrl + "/" + id, Pacjent.class);
        model.addAttribute("title", "Edytuj Pacjenta");
        model.addAttribute("fields", List.of("imie", "nazwisko", "dataUrodzenia", "telefon", "email"));
        model.addAttribute("entity", pacjent);
        model.addAttribute("actionUrl", "/pacjenci/edit/" + id);
        return "base-form";
    }


    @GetMapping("/delete/{id}")
    public String deletePacjent(@PathVariable String id, Model model) {
        try {
            restTemplate.delete(apiUrl + "/" + id);
            model.addAttribute("message", "Pacjent został pomyślnie usunięty.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            model.addAttribute("message", "Nie udało się usunąć pacjenta. Spróbuj ponownie.");
            model.addAttribute("messageType", "error");
        }
        return "redirect:/pacjenci";
    }

}
