package com.group.docReservations.view;

import com.group.docReservations.classes.Pacjent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/pacjents")
public class PacjentView {

    @Value(value = "${server.url}")
    private String serverUrl;

    private final RestTemplate restTemplate;
    private final String apiUrl = serverUrl+"api/pacjents";

    @Autowired
    public PacjentView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String listPacjents(Model model) {
        List<Pacjent> pacjenci = Arrays.asList(restTemplate.getForObject(apiUrl, Pacjent[].class));
        model.addAttribute("pacjenci", pacjenci);
        return "pacjenci"; // Refers to pacjenci.html
    }

    @GetMapping("/add")
    public String showAddPacjentForm(Model model) {
        model.addAttribute("pacjent", new Pacjent());
        return "add-pacjent";
    }

    @PostMapping("/add")
    public String addPacjent(@Valid @ModelAttribute("pacjent") Pacjent pacjent) {
        restTemplate.postForObject(apiUrl, pacjent, Pacjent.class);
        return "redirect:/pacjents";
    }

    @GetMapping("/edit/{id}")
    public String showEditPacjentForm(@PathVariable String id, Model model) {
        Pacjent pacjent = restTemplate.getForObject(apiUrl + "/" + id, Pacjent.class);
        model.addAttribute("pacjent", pacjent);
        return "edit-pacjent";
    }

    @PostMapping("/edit/{id}")
    public String updatePacjent(@PathVariable String id, @Valid @ModelAttribute("pacjent") Pacjent pacjent) {
        restTemplate.put(apiUrl + "/" + id, pacjent);
        return "redirect:/pacjents";
    }

    @GetMapping("/delete/{id}")
    public String deletePacjent(@PathVariable String id) {
        restTemplate.delete(apiUrl + "/" + id);
        return "redirect:/pacjents";
    }
}
