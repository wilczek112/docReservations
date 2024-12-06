package com.group.docReservations.config;

import com.group.docReservations.classes.*;
import com.group.docReservations.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Configuration
public class DataGenerator {

    @Value("${db.generate.enabled:false}")
    private boolean seedEnabled;

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
            //userRepository.deleteAll();
            specjalizacjaRepository.deleteAll();
            lekarzRepository.deleteAll();
            harmonogramRepository.deleteAll();
            wizytaRepository.deleteAll();

            // Seed Users
            User user1 = new User();
            user1.setLogin("jdoe");
            user1.setPassword("password123");
            user1.setFirstName("John");
            user1.setLastName("Doe");
            user1.setEmail("johndoe@example.com");
            user1.setPhone("+1234567890");
            user1.setRole("USER");
            user1.setBirthDate(LocalDate.of(1990, 1, 1));

            User user2 = new User();
            user2.setLogin("asmith");
            user2.setPassword("password123");
            user2.setFirstName("Anna");
            user2.setLastName("Smith");
            user2.setEmail("annasmith@example.com");
            user2.setPhone("+9876543210");
            user2.setRole("USER");
            user2.setBirthDate(LocalDate.of(1985, 5, 15));

            userRepository.saveAll(Arrays.asList(user1, user2));

            // Seed Specjalizacje
            Specjalizacja spec1 = new Specjalizacja();
            spec1.setNazwa("Cardiology");

            Specjalizacja spec2 = new Specjalizacja();
            spec2.setNazwa("Neurology");

            specjalizacjaRepository.saveAll(Arrays.asList(spec1, spec2));

            // Seed Lekarze
            Lekarz lekarz1 = new Lekarz();
            lekarz1.setUserId(user1.getId());
            lekarz1.setSpecjalizacjaId(spec1.getId());

            Lekarz lekarz2 = new Lekarz();
            lekarz2.setUserId(user2.getId());
            lekarz2.setSpecjalizacjaId(spec2.getId());

            lekarzRepository.saveAll(Arrays.asList(lekarz1, lekarz2));

            // Seed Harmonogram
            Harmonogram harmonogram1 = new Harmonogram();
            harmonogram1.setLekarz_id(Integer.parseInt(lekarz1.getId()));
            harmonogram1.setData(new Date());
            harmonogram1.setGodzina_od(Time.valueOf("09:00:00"));
            harmonogram1.setGodzina_do(Time.valueOf("12:00:00"));

            Harmonogram harmonogram2 = new Harmonogram();
            harmonogram2.setLekarz_id(Integer.parseInt(lekarz2.getId()));
            harmonogram2.setData(new Date());
            harmonogram2.setGodzina_od(Time.valueOf("13:00:00"));
            harmonogram2.setGodzina_do(Time.valueOf("17:00:00"));

            harmonogramRepository.saveAll(Arrays.asList(harmonogram1, harmonogram2));

            // Seed Wizyta
            Wizyta wizyta1 = new Wizyta();
            wizyta1.setPacjent_id(Integer.parseInt(user1.getId()));
            wizyta1.setLekarz_id(Integer.parseInt(lekarz1.getId()));
            wizyta1.setData_wizyty(new Date());
            wizyta1.setStatus("SCHEDULED");

            Wizyta wizyta2 = new Wizyta();
            wizyta2.setPacjent_id(Integer.parseInt(user2.getId()));
            wizyta2.setLekarz_id(Integer.parseInt(lekarz2.getId()));
            wizyta2.setData_wizyty(new Date());
            wizyta2.setStatus("COMPLETED");

            wizytaRepository.saveAll(Arrays.asList(wizyta1, wizyta2));

            System.out.println("Database seeding completed!");
        };
    }
}
