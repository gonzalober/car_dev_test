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
  public ResponseEntity<List<Cars>> findAll() {
    List<Cars> items = service.findAll();
    return ResponseEntity.ok().body(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cars> find(@PathVariable("id") Long id) {
    Optional<Cars> item = service.find(id);
    return ResponseEntity.of(item);
  }

  // post
  @PostMapping
  public ResponseEntity<Cars> create(@RequestBody Cars cars) {
    Cars created = service.create(cars);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();
    return ResponseEntity.created(location).body(created);
  }

  // PUT
  @PutMapping("/{id}")
  public ResponseEntity<Cars> update(
      @PathVariable("id") Long id,
      @RequestBody Cars updatedItem) {

    Optional<Cars> updated = service.update(id, updatedItem);

    return updated
        .map(value -> ResponseEntity.ok().body(value))
        .orElseGet(() -> {
          Cars created = service.create(updatedItem);
          URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(created.getId())
              .toUri();
          return ResponseEntity.created(location).body(created);
        });
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Cars> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
