package com.freedom.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HealthApplication {

  public static void main(String[] args) {
    SpringApplication.run(HealthApplication.class, args);
  }
}
