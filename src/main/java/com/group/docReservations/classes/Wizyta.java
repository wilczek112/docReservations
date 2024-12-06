package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Document(collection = "wizyta")
public class Wizyta {
    @Id
    private String id;

    private String user_id;
    private String lekarz_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start_time;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime end_time;

    private String status;

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(start_time.toLocalDate(), end_time);
    }
}
