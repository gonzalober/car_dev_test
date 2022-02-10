package com.example.restservice;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarConfig {

  @Bean
  CommandLineRunner commandLineRunner(CarsRepository repository) {
    return args -> {
      Car ford = new Car(1L, "ford", "F100", "yellow", 1986);
      repository.saveAll(
          List.of(ford));
    };
  }
}