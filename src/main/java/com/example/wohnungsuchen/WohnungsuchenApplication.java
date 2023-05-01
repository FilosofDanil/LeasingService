package com.example.wohnungsuchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WohnungsuchenApplication {

    public static void main(String[] args) {
        SpringApplication.run(WohnungsuchenApplication.class, args);
    }

}
