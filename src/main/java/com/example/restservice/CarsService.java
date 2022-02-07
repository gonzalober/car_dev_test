package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Repository
@EnableMapRepositories
public class CarsService {

  private final CrudRepository<Car, Long> repository;

  public CarsService(@Autowired CrudRepository<Car, Long> repository) {
    this.repository = repository;
    this.repository.saveAll(defaultItems());
  }

  private static List<Car> defaultItems() {
    return List.of(new Car(1L, "make", "model", "colour", 1L));
  }

  public List<Car> findAll() {
    List<Car> list = new ArrayList<>();
    System.out.println("CARSSERVICE==> 1" + repository);
    Iterable<Car> car = repository.findAll();
    System.out.println("CARSSERVICE===>2" + car);
    car.forEach(list::add);
    return list;
  }

  public Optional<Car> find(Long id) {
    return repository.findById(id);
  }

  public Car create(Car cars) {
    // To ensure the item ID remains unique,
    // use the current timestamp.
    Car copy = new Car(
        new Date().getTime(),
        cars.getMake(),
        cars.getModel(),
        cars.getColour(),
        cars.getYear());
    return repository.save(copy);
  }

  public Optional<Car> update(Long id, Car newCar) {
    // Only update a car if it can be found first.
    return repository.findById(id)
        .map(oldCar -> {
          Car updated = oldCar.updateWith(newCar);
          return repository.save(updated);
        });
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

}
