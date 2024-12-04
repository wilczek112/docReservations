package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String login;

    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number.")
    private String phone;

    private String role;

    private LocalDate birthDate;

    @Transient
    private String confirmPassword; // Excluded from MongoDB persistence
}
