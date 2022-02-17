package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

  public Optional<Car> getCarById(Long id) {
    return carsRepository.findById(id);
  }

  public void addNewCar(Car car) {
    carsRepository.save(car);
  }

  public boolean deleteCar(Long id) {
    carsRepository.deleteById(id);
    return true;
  }

  @Transactional
  public void updateCar(Car car) {
    carsRepository.save(car);
  }

}
