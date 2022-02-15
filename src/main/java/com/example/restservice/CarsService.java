package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

  public Optional<Car> getCarsById(Long id) {
    return carsRepository.findById(id);
  }

  public void addNewCar(Car car) {
    String makeCar = car.getMake();
    String modelCar = car.getModel();
    String colourCar = car.getColour();
    Integer yearCar = car.getYear();
    if (makeCar == null || modelCar == null || colourCar == null || yearCar == null) {
      throw new ApiRequestException("bad request. Mandatory car attributes are missing");
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
  public void updateCar(Long carId, String model, String make) {
    Car car = carsRepository.findById(carId)
        .orElseThrow(() -> new IllegalStateException("car with id " + carId + " does not exist"));

    if (model != null && model.length() > 0 && !Objects.equals(car.getModel(), model)) {
      car.setModel(model);
    }

    if (make != null && make.length() > 0 && !Objects.equals(car.getMake(), make)) {
      car.setMake(make);
    }
  }

}
