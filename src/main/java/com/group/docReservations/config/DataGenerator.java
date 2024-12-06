package com.group.docReservations.config;

import com.group.docReservations.classes.*;
import com.group.docReservations.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
public class DataGenerator {

    @Value("${db.generate.enabled:false}")
    private boolean seedEnabled;

    private final BCryptPasswordEncoder passwordEncoder;

    public DataGenerator(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner runner(
            UserRepository userRepository,
            SpecjalizacjaRepository specjalizacjaRepository,
            LekarzRepository lekarzRepository,
            HarmonogramRepository harmonogramRepository,
            WizytaRepository wizytaRepository) {

        return args -> {
            if (!seedEnabled) {
                System.out.println("Database seeder is disabled. Skipping seeding process.");
                return;
            }

            System.out.println("Starting database seeding...");

            // Clear existing data
            specjalizacjaRepository.deleteAll();
            lekarzRepository.deleteAll();
            harmonogramRepository.deleteAll();
            wizytaRepository.deleteAll();
            userRepository.deleteAll();

            Random random = new Random();

            // Seed Admin
            User admin = new User();
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("Admin-112"));
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEmail("admin@example.com");
            admin.setPhone("+1111111111");
            admin.setRole("ROLE_ADMIN");
            admin.setBirthDate(LocalDate.of(1980, 1, 1));

            // Seed Regular User
            User user = new User();
            user.setLogin("user");
            user.setPassword(passwordEncoder.encode("User-112"));
            user.setFirstName("Regular");
            user.setLastName("User");
            user.setEmail("user@example.com");
            user.setPhone("+2222222222");
            user.setBirthDate(LocalDate.of(1990, 1, 1));

            // Generate random users (patients)
            List<User> patients = new ArrayList<>(List.of(user));
            for (int i = 1; i <= 3; i++) {
                User newUser = new User();
                newUser.setLogin("patient" + i);
                newUser.setPassword(passwordEncoder.encode("password" + i));
                newUser.setFirstName("PatientFirstName" + i);
                newUser.setLastName("PatientLastName" + i);
                newUser.setEmail("patient" + i + "@example.com");
                newUser.setPhone("+12345678" + i);
                newUser.setBirthDate(LocalDate.of(1990 + i, 1, 1));
                patients.add(newUser);
            }
            userRepository.saveAll(List.of(admin)); // Save admin separately
            userRepository.saveAll(patients);

            // Seed Specjalizacje
            Specjalizacja cardio = new Specjalizacja();
            cardio.setNazwa("Cardiology");

            Specjalizacja neuro = new Specjalizacja();
            neuro.setNazwa("Neurology");

            Specjalizacja ortho = new Specjalizacja();
            ortho.setNazwa("Orthopedics");

            List<Specjalizacja> specjalizacje = List.of(cardio, neuro, ortho);
            specjalizacjaRepository.saveAll(specjalizacje);

            // Generate random doctors
            List<User> doctors = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                User doctorUser = new User();
                doctorUser.setLogin("doctor" + i);
                doctorUser.setPassword("password" + i);
                doctorUser.setFirstName("DoctorFirstName" + i);
                doctorUser.setLastName("DoctorLastName" + i);
                doctorUser.setEmail("doctor" + i + "@example.com");
                doctorUser.setPhone("+98765432" + i);
                doctorUser.setRole("ROLE_DOCTOR"); // Mark as a doctor
                doctorUser.setBirthDate(LocalDate.of(1980 + i, 1, 1));
                doctors.add(doctorUser);
            }
            userRepository.saveAll(doctors);

            // Seed Lekarze
            List<Lekarz> lekarze = new ArrayList<>();
            for (int i = 0; i < doctors.size(); i++) {
                Lekarz lekarz = new Lekarz();
                lekarz.setUserId(doctors.get(i).getId());
                lekarz.setSpecjalizacjaId(specjalizacje.get(i % specjalizacje.size()).getId());
                lekarze.add(lekarz);
            }
            lekarzRepository.saveAll(lekarze);

            // Seed Harmonograms
            List<Harmonogram> harmonograms = new ArrayList<>();
            for (Lekarz lekarz : lekarze) {
                Harmonogram harmonogram = new Harmonogram();
                harmonogram.setLekarz_id(lekarz.getId());
                harmonogram.setDays(Arrays.asList(DayOfWeek.values()).subList(0, random.nextInt(5) + 2)); // Random days
                harmonogram.setStart_time(LocalTime.of(9, 0));
                harmonogram.setEnd_time(LocalTime.of(17, 0));
                harmonograms.add(harmonogram);
            }
            harmonogramRepository.saveAll(harmonograms);

            // Seed Wizyty
            List<Wizyta> wizyty = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Wizyta wizyta = new Wizyta();
                wizyta.setUser_id(patients.get(random.nextInt(patients.size())).getId());
                wizyta.setLekarz_id(lekarze.get(random.nextInt(lekarze.size())).getId());
                wizyta.setStart_time(LocalDateTime.of(2024, 12, random.nextInt(20) + 1, random.nextInt(8) + 9, 0));
                wizyta.setEnd_time(wizyta.getStart_time().toLocalTime().plusHours(1));
                wizyta.setStatus(random.nextBoolean() ? "SCHEDULED" : "COMPLETED");
                wizyty.add(wizyta);
            }
            wizytaRepository.saveAll(wizyty);

            System.out.println("Database seeding completed!");
        };
    }
}
