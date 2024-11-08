package com.group.docReservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
@SessionAttributes
public class docReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(docReservationsApplication.class, args);
	}
}
