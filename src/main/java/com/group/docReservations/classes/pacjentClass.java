package com.group.docReservations.classes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Document(collection = "pacjent")
public class pacjentClass {
    @Id
    private String pacjent_id;

    private String imie;
    private String nazwisko;
    private Date data_urodzenia;
    private String telefon;
    private String email;
}
