package com.group.docReservations.classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end_time;

    private String status;

    public void setEnd_time(LocalDateTime start_time, int durationMinutes) {
        this.end_time = start_time.plusMinutes(durationMinutes);
    }

    @Transient
    private Lekarz lekarz;
}
