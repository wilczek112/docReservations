package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "lekarz")
public class lekarzClass {
    @Id
    private String lekarz_id;
    private int specjalizacja_id;
}
