package com.debbech.devwall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DevWallApp {

	public static void main(String[] args) {
		SpringApplication.run(DevWallApp.class, args);
	}

}
