package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "wizyta")
public class Wizyta {
    @Id
    private String wizyta_id;

    private int pacjent_id;
    private int lekarz_id;
    private Date data_wizyty;
    private String status;
}
