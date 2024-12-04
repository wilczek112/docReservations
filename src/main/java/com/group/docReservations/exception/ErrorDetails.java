package com.group.docReservations.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String description;

    public ErrorDetails(int statusCode, String message, String description) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }
}
