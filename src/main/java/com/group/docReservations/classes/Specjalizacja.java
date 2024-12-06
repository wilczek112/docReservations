package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "specjalizacja")
public class Specjalizacja {
    @Id
    private String id;

    private String nazwa;
}
