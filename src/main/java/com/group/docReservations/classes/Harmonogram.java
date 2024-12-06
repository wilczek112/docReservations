package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Document(collection = "harmonogram")
public class Harmonogram {
    @Id
    private String id;

    private int lekarz_id;
    private Date data;
    private Time godzina_od;
    private Time godzina_do;
}
