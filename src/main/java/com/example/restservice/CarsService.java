package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.exception.ApiRequestException;

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
    String makeCar = car.getMake();
    String modelCar = car.getModel();
    String colourCar = car.getColour();
    Integer yearCar = car.getYear();
    if (makeCar == null || modelCar == null || colourCar == null || yearCar == null) {
      throw new ApiRequestException("bad request. Mandatory car attributes are missing");// 400 move to controller
    }
    carsRepository.save(car);
  }

  public boolean deleteCar(Long id) {
    boolean exists = carsRepository.existsById(id);
    if (!exists) {
      throw new IllegalStateException("car with id " + id + " does not exist");
    }
    carsRepository.deleteById(id);
    return true;
  }

  @Transactional
  public void updateCar(Car car) {

    Car carToUpdate = carsRepository.findById(car.getId())
        .orElseThrow(() -> new IllegalStateException("car with id " + car.getId() + " does not exist"));

    carToUpdate.setCar(car);

  }

}
