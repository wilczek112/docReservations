package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "lekarze")
public class Lekarz {
    @Id
    private String id;

    private String userId;
    private String specjalizacjaId;

    @Transient
    private User user;
}
