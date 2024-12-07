package com.group.docReservations.controller;

import com.group.docReservations.classes.Harmonogram;
import com.group.docReservations.classes.Lekarz;
import com.group.docReservations.classes.User;
import com.group.docReservations.classes.Wizyta;
import com.group.docReservations.exception.ResourceNotFoundException;
import com.group.docReservations.services.HarmonogramService;
import com.group.docReservations.services.LekarzService;
import com.group.docReservations.services.UserService;
import com.group.docReservations.services.WizytaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final HarmonogramService harmonogramService;
    private final WizytaService wizytaService;
    private final UserService userService;
    private final LekarzService lekarzService;

    @Autowired
    public ReservationController(HarmonogramService harmonogramService, WizytaService wizytaService, UserService userService, LekarzService lekarzService) {
        this.harmonogramService = harmonogramService;
        this.wizytaService = wizytaService;
        this.userService = userService;
        this.lekarzService = lekarzService;
    }

    /**
     * Get available time slots for a specific doctor on a specific day.
     *
     * @param doctorId The ID of the doctor.
     * @param date     The date to find available slots for.
     * @return A list of available time slots.
     */
    @GetMapping("/available-slots")
    public Map<String, Object> getAvailableSlots(
            @RequestParam("doctorId") String doctorId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        // Fetch the Lekarz entity and its associated user details
        Lekarz lekarz = lekarzService.findLekarzById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID " + doctorId));
        User doctorUser = userService.findUserById(lekarz.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for doctor with ID " + doctorId));

        // Fetch the harmonogram for the doctor
        List<Harmonogram> harmonograms = harmonogramService.findAllHarmonograms()
                .stream()
                .filter(h -> h.getLekarz_id().equals(doctorId) && h.getDays().contains(date.getDayOfWeek()))
                .collect(Collectors.toList());

        if (harmonograms.isEmpty()) {
            return Map.of(
                    "doctorName", doctorUser.getFirstName() + " " + doctorUser.getLastName(),
                    "availableSlots", Collections.emptyList() // No working hours for the given date
            );
        }

        // Fetch existing reservations for the doctor
        List<Wizyta> reservations = wizytaService.findAllWizyty()
                .stream()
                .filter(w -> w.getLekarz_id().equals(doctorId) &&
                        w.getStart_time().toLocalDate().isEqual(date))
                .collect(Collectors.toList());

        // Generate free time slots for each harmonogram
        List<LocalTime> availableSlots = new ArrayList<>();
        for (Harmonogram harmonogram : harmonograms) {
            LocalTime start = harmonogram.getStart_time();
            LocalTime end = harmonogram.getEnd_time();

            while (start.plusMinutes(30).isBefore(end) || start.plusMinutes(30).equals(end)) {
                LocalTime slotEnd = start.plusMinutes(30);

                // Adjusted overlap logic
                LocalTime finalStart = start;
                boolean isFree = reservations.stream().noneMatch(reservation ->
                        finalStart.isBefore(reservation.getEnd_time().toLocalTime()) &&
                                slotEnd.isAfter(reservation.getStart_time().toLocalTime())
                );

                if (isFree) {
                    availableSlots.add(start);
                }

                start = slotEnd; // Move to the next slot
            }
        }

        return Map.of(
                "doctorName", doctorUser.getFirstName() + " " + doctorUser.getLastName(),
                "availableSlots", availableSlots
        );
    }

    @PostMapping("/create")
    public String createReservation(
            @RequestParam("doctorId") String doctorId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("time") @DateTimeFormat(pattern = "HH:mm") LocalTime time,
            Authentication authentication) throws Exception {

        // Get the logged-in user's details
        String currentUsername = authentication.getName();
        User currentUser = userService.findUserByEmail(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for email " + currentUsername));

        // Verify the doctor exists
        Lekarz lekarz = lekarzService.findLekarzById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID " + doctorId));

        // Create the reservation
        LocalDateTime startDateTime = LocalDateTime.of(date, time);
        LocalDateTime endDateTime = startDateTime.plusMinutes(30);

        Wizyta newWizyta = new Wizyta();
        newWizyta.setUser_id(currentUser.getId());
        newWizyta.setLekarz_id(lekarz.getId());
        newWizyta.setStart_time(startDateTime);
        newWizyta.setEnd_time(endDateTime);
        newWizyta.setStatus("SCHEDULED");

        // Save the reservation
        wizytaService.saveWizyta(newWizyta);

        return "redirect:/panel?success=reservation_created";
    }

}
