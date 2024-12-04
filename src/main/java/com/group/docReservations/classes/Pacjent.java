package com.group.docReservations.classes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Document(collection = "pacjent")
public class Pacjent {
    @Id
    private String pacjentId;

    private String imie;
    private String nazwisko;
    private Date dataUrodzenia;
    private String telefon;
    private String email;
}
