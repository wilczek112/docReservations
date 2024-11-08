package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class userClass {
    @Id
    private String id;

    private String login;
    private String password;
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private String role;

    @Transient
    private String confirmPassword; // Transient to avoid storing in MongoDB
}
