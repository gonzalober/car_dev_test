package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarsController {
  private static final String template = "Cars, %s!";
  private final AtomicLong counter = new AtomicLong();

  // @GetMapping("/cars")
  // public Cars cars(@RequestParam(value = "name", defaultValue = "HERE") String
  // name) {
  // // System.out.println(name);
  // return new Cars(counter.incrementAndGet(),
  // String.format(template, name));
  // }
}
