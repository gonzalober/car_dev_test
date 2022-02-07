package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/menu/cars")
public class CarsController {
  private final CarsService service;

  public CarsController(CarsService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Car>> findAll() {
    List<Car> cars = service.findAll();
    System.out.println("CONTROLLER ------>>>>>");
    return ResponseEntity.ok().body(cars);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Car> find(@PathVariable("id") Long id) {
    Optional<Car> item = service.find(id);
    return ResponseEntity.of(item);
  }

  // post
  @PostMapping
  public ResponseEntity<Car> create(@RequestBody Car cars) {
    Car created = service.create(cars);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();
    return ResponseEntity.created(location).body(created);
  }

  // PUT
  @PutMapping("/{id}")
  public ResponseEntity<Car> update(
      @PathVariable("id") Long id,
      @RequestBody Car updatedItem) {

    Optional<Car> updated = service.update(id, updatedItem);

    return updated
        .map(value -> ResponseEntity.ok().body(value))
        .orElseGet(() -> {
          Car created = service.create(updatedItem);
          URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(created.getId())
              .toUri();
          return ResponseEntity.created(location).body(created);
        });
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Car> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
