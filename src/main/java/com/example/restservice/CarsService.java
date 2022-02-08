package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService {

  private final CarsRepository carsRepository;

  @Autowired
  public CarsService(CarsRepository carsRepository) {
    this.carsRepository = carsRepository;
  }

  public List<Car> getCars() {
    return carsRepository.findAll();
  }

  public void addNewCar(Car car) {
    System.out.println("sevie");
    carsRepository.save(car);
  }
}
