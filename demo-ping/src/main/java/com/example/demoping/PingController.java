package com.example.demoping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class PingController {

    // @Autowired
    // private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ping")
    public String ping() {
        return "Ping @ " + Instant.now();
    }

    @PostMapping("/ping/{count}")
    public String pingCount(@PathVariable int count) {
        //"http://pong/pong"
        return IntStream.range(0, count)
                .mapToObj(i -> restTemplate.getForObject("http://demo-pong.apps.internal:8080/pong", String.class))
                .collect(Collectors.joining("\n - ", "Calling " + count + " times\n - ", "\nDone!\n"));
    }
}
