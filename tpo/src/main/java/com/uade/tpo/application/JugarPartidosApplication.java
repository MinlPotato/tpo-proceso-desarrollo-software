package com.uade.tpo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JugarPartidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(JugarPartidosApplication.class, args);
	}

}
