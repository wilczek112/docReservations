package com.group.docReservations.controller;

import com.group.docReservations.classes.Harmonogram;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.HarmonogramService;
import com.group.docReservations.services.LekarzService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/harmonogramy")
public class HarmonogramController {

    private final HarmonogramService harmonogramService;
    private final LekarzService lekarzService;

    @Autowired
    public HarmonogramController(HarmonogramService harmonogramService, LekarzService lekarzService) {
        this.harmonogramService = harmonogramService;
        this.lekarzService = lekarzService;
    }

    @GetMapping
    public String listHarmonograms(Model model) {
        List<Harmonogram> harmonograms = harmonogramService.findAllHarmonograms();
        model.addAttribute("title", "Lista Harmonogramów");
        model.addAttribute("headers", Arrays.asList("Lekarz ID", "Dni", "Godzina rozpoczęcia", "Godzina zakończenia"));
        model.addAttribute("fields", Arrays.asList("lekarz_id", "days", "start_time", "end_time"));
        model.addAttribute("entities", harmonograms);
        model.addAttribute("editUrl", "/harmonogramy/edit");
        model.addAttribute("deleteUrl", "/harmonogramy/delete");
        model.addAttribute("addUrl", "/harmonogramy/add");
        return "base-crud";
    }

    @GetMapping("/add")
    public String showAddHarmonogramForm(Model model) {
        model.addAttribute("title", "Dodaj Harmonogram");
        model.addAttribute("entity", new Harmonogram());
        model.addAttribute("actionUrl", "/harmonogramy/add");

        populateFields(model);
        return "base-form";
    }

    @PostMapping("/add")
    public String addHarmonogram(@Valid @ModelAttribute("entity") Harmonogram harmonogram, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateFields(model);
            model.addAttribute("title", "Dodaj Harmonogram");
            model.addAttribute("actionUrl", "/harmonogramy/add");
            return "base-form";
        }
        try {
            harmonogramService.saveHarmonogram(harmonogram);
            model.addAttribute("message", "Harmonogram został dodany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateFields(model);
            model.addAttribute("message", "Nie udało się dodać harmonogramu.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/harmonogramy";
    }

    @GetMapping("/edit/{id}")
    public String showEditHarmonogramForm(@PathVariable String id, Model model) {
        Harmonogram harmonogram = harmonogramService.findHarmonogramById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Harmonogram not found with id " + id));

        model.addAttribute("title", "Edytuj Harmonogram");
        model.addAttribute("entity", harmonogram);
        model.addAttribute("actionUrl", "/harmonogramy/edit/" + id);

        populateFields(model);
        return "base-form";
    }

    @PostMapping("/edit/{id}")
    public String editHarmonogram(@PathVariable String id, @Valid @ModelAttribute("entity") Harmonogram harmonogram, BindingResult result, Model model) {
        if (result.hasErrors()) {
            populateFields(model);
            model.addAttribute("title", "Edytuj Harmonogram");
            model.addAttribute("actionUrl", "/harmonogramy/edit/" + id);
            return "base-form";
        }
        try {
            harmonogram.setId(id);
            harmonogramService.updateHarmonogram(harmonogram);
            model.addAttribute("message", "Harmonogram zaktualizowany pomyślnie.");
            model.addAttribute("messageType", "success");
        } catch (Exception e) {
            populateFields(model);
            model.addAttribute("message", "Nie udało się zaktualizować harmonogramu.");
            model.addAttribute("messageType", "error");
            return "base-form";
        }
        return "redirect:/harmonogramy";
    }

    @GetMapping("/delete/{id}")
    public String deleteHarmonogram(@PathVariable String id) {
        harmonogramService.deleteHarmonogram(id);
        return "redirect:/harmonogramy";
    }

    private void populateFields(Model model) {
        model.addAttribute("fields", Arrays.asList(
                Map.of("name", "lekarz_id", "label", "Lekarz", "type", "dropdown", "placeholder", "Wybierz lekarza", "options", lekarzService.findAllLekarze()
                        .stream()
                        .map(lekarz -> Map.of("value", lekarz.getId(), "label", lekarz.getUserId()))
                        .toList()),
                Map.of("name", "days", "label", "Dni tygodnia", "type", "checkbox-group", "placeholder", "Wybierz dni", "options", Arrays.stream(DayOfWeek.values())
                        .map(day -> Map.of("value", day.name(), "label", day.name()))
                        .toList()),
                Map.of("name", "start_time", "label", "Godzina rozpoczęcia", "type", "time"),
                Map.of("name", "end_time", "label", "Godzina zakończenia", "type", "time")
        ));
    }
}
