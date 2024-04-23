package com.gaenari.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExerciseSettingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExerciseSettingServiceApplication.class, args);
	}

}
