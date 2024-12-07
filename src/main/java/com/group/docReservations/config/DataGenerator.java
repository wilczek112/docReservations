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

            // Save Admin and User
            userRepository.saveAll(List.of(admin, user));

            // Seed Specjalizacje
            Specjalizacja cardio = new Specjalizacja();
            cardio.setNazwa("Cardiology");

            Specjalizacja neuro = new Specjalizacja();
            neuro.setNazwa("Neurology");

            Specjalizacja ortho = new Specjalizacja();
            ortho.setNazwa("Orthopedics");

            List<Specjalizacja> specjalizacje = List.of(cardio, neuro, ortho);
            specjalizacjaRepository.saveAll(specjalizacje);

            // Seed Doctors
            List<User> doctors = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                User doctor = new User();
                doctor.setLogin("doctor" + i);
                doctor.setPassword(passwordEncoder.encode("Doctor-" + i));
                doctor.setFirstName("DoctorFirstName" + i);
                doctor.setLastName("DoctorLastName" + i);
                doctor.setEmail("doctor" + i + "@example.com");
                doctor.setPhone("+98765432" + i);
                doctor.setRole("ROLE_DOCTOR");
                doctor.setBirthDate(LocalDate.of(1980 + i, 1, 1));
                doctors.add(doctor);
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

            // Seed Static Harmonograms
            List<Harmonogram> harmonograms = new ArrayList<>();

            Harmonogram harmonogram1 = new Harmonogram();
            harmonogram1.setLekarz_id(lekarze.get(0).getId());
            harmonogram1.setDays(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
            harmonogram1.setStart_time(LocalTime.of(9, 0));
            harmonogram1.setEnd_time(LocalTime.of(13, 0));
            harmonograms.add(harmonogram1);

            Harmonogram harmonogram2 = new Harmonogram();
            harmonogram2.setLekarz_id(lekarze.get(1).getId());
            harmonogram2.setDays(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
            harmonogram2.setStart_time(LocalTime.of(10, 0));
            harmonogram2.setEnd_time(LocalTime.of(14, 0));
            harmonograms.add(harmonogram2);

            Harmonogram harmonogram3 = new Harmonogram();
            harmonogram3.setLekarz_id(lekarze.get(2).getId());
            harmonogram3.setDays(List.of(DayOfWeek.FRIDAY));
            harmonogram3.setStart_time(LocalTime.of(11, 0));
            harmonogram3.setEnd_time(LocalTime.of(15, 0));
            harmonograms.add(harmonogram3);

            harmonogramRepository.saveAll(harmonograms);

            // Seed Static Wizyty
            List<Wizyta> wizyty = new ArrayList<>();
            for (int i = 0; i < lekarze.size(); i++) {
                Lekarz lekarz = lekarze.get(i);
                List<DayOfWeek> workingDays = harmonograms.get(i).getDays();

                for (DayOfWeek day : workingDays) {
                    LocalDate date = LocalDate.of(2024, 12, 1).with(day);
                    LocalDateTime firstSlotStart = date.atTime(harmonograms.get(i).getStart_time());
                    LocalDateTime secondSlotStart = firstSlotStart.plusHours(1);

                    // First visit of the day
                    Wizyta wizyta1 = new Wizyta();
                    wizyta1.setUser_id(user.getId());
                    wizyta1.setLekarz_id(lekarz.getId());
                    wizyta1.setStart_time(firstSlotStart);
                    wizyta1.setEnd_time(firstSlotStart.plusMinutes(30));
                    wizyta1.setStatus("SCHEDULED");
                    wizyty.add(wizyta1);

                    // Second visit of the day
                    Wizyta wizyta2 = new Wizyta();
                    wizyta2.setUser_id(user.getId());
                    wizyta2.setLekarz_id(lekarz.getId());
                    wizyta2.setStart_time(secondSlotStart);
                    wizyta2.setEnd_time(secondSlotStart.plusMinutes(30));
                    wizyta2.setStatus("SCHEDULED");
                    wizyty.add(wizyta2);
                }
            }

            wizytaRepository.saveAll(wizyty);

            System.out.println("Database seeding completed!");
        };
    }
}
