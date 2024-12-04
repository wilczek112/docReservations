package com.group.docReservations.controller;

import com.group.docReservations.classes.Pacjent;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.PacjentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacjenci")
public class PacjentController {

    @Autowired
    private PacjentService pacjentService;

    @PostMapping
    public Pacjent createPacjent(@RequestBody Pacjent pacjent) {
        return pacjentService.savePacjent(pacjent);
    }

    @GetMapping
    public List<Pacjent> getAllPacjents() {
        return pacjentService.findAllPacjents();
    }

    @GetMapping("/{id}")
    public Pacjent getPacjentById(@PathVariable String id) {
        return pacjentService.findPacjentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pacjent not found with id " + id));
    }

    @PutMapping("/{id}")
    public Pacjent updatePacjent(@PathVariable String id, @RequestBody Pacjent updatedPacjent) {
        return pacjentService.updatePacjent(id, updatedPacjent);
    }

    @DeleteMapping("/{id}")
    public void deletePacjent(@PathVariable String id) {
        pacjentService.deletePacjent(id);
    }
}
