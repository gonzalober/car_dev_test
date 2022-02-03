package com.example.restservice;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class CarsService {
  private final CrudRepository<Cars, Long> repository;

  public CarsService(CrudRepository<Cars, Long> repository) {

    this.repository = repository;

    this.repository.saveAll(defaultItems());
  }

  private static List<Cars> defaultItems() {
    return List.of(new Cars(1, "make", "model", "colour", 1));
  }

  public List<Cars> findAll() {
    List<Cars> list = new ArrayList<>();
    Iterable<Cars> items = repository.findAll();
    items.forEach(list::add);
    return list;
  }

  public Optional<Cars> find(Long id) {
    return repository.findById(id);
  }

  public Cars create(Cars cars) {
    // To ensure the item ID remains unique,
    // use the current timestamp.
    Cars copy = new Cars(
        new Date().getTime(),
        cars.getMake(),
        cars.getModel(),
        cars.getColour(),
        cars.getYear());
    return repository.save(copy);
  }

  public Optional<Cars> update(Long id, Cars newCars) {
    // Only update an item if it can be found first.
    return repository.findById(id)
        .map(oldCar -> {
          Cars updated = oldCar.updateWith(newCars);
          return repository.save(updated);
        });
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

}
