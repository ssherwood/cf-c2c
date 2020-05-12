package com.example.demopong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@SpringBootApplication
@RestController
public class DemoPongApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(DemoPongApplication.class, args);
    }

    @GetMapping("/pong")
    public String pong() {
        String cfInstance = environment.getProperty("CF_INSTANCE_INDEX");
        return "Pong[" + cfInstance + "] @ " + Instant.now();
    }

}
