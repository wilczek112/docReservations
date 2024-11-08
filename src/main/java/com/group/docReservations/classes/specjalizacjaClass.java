package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "specjalizacja")
public class specjalizacjaClass {
    @Id
    private String specjalizacja_id;

    private String nazwa;
}
