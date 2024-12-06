package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "harmonogram")
public class Harmonogram {
    @Id
    private String id;

    private String lekarz_id;

    private List<DayOfWeek> days;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime start_time;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime end_time;
}



