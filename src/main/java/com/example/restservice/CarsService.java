package com.example.restservice;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
// import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@EnableMapRepositories
public class CarsService {
  // @Autowired
  private final CrudRepository<Cars, Long> repository;

  public CarsService(CrudRepository<Cars, Long> repository) {
    this.repository = repository;
    this.repository.saveAll(defaultItems());
  }

  private static List<Cars> defaultItems() {
    return List.of(new Cars(1L, "make", "model", "colour", 1L));
  }

  public List<Cars> findAll() {
    List<Cars> list = new ArrayList<>();
    Iterable<Cars> cars = repository.findAll();
    cars.forEach(list::add);
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

  public Optional<Cars> update(Long id, Cars newCar) {
    // Only update a car if it can be found first.
    return repository.findById(id)
        .map(oldCar -> {
          Cars updated = oldCar.updateWith(newCar);
          return repository.save(updated);
        });
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

}
