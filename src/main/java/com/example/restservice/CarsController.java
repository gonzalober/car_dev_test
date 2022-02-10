package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/menu/cars")
public class CarsController {

  private final CarsService carService;

  @Autowired
  public CarsController(CarsService carService) {
    this.carService = carService;
  }

  // get
  @GetMapping
  public List<Car> getCars() {
    return carService.getCars();
  }

  // post
  @PostMapping
  public void addNewCar(@RequestBody Car car) {
    carService.addNewCar(car);
  }

  // delete
  @DeleteMapping("{carId}")
  public void deleteCar(@PathVariable("carId") Long carId) {
    System.out.println("HELOO");
    carService.deleteCar(carId);
  }

  // put
  @PutMapping("{carId}")
  public void updateCar(
      @PathVariable("carId") Long carId,
      @RequestParam(required = false) String model,
      @RequestParam(required = false) String make) {
    carService.updateCar(carId, model, make);
  }

}
