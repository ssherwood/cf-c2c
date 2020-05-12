package com.example.demoping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication
//@Profile("foo")
public class DemoPingApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(DemoPingApplication.class, args);
	}
}
