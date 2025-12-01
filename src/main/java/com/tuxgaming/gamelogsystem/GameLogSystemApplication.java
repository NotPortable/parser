package com.tuxgaming.gamelogsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameLogSystemApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GameLogSystemApplication.class, args);
    }
}
