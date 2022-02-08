package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/menu/cars")
public class CarsController {

  private final CarsService service;

  @Autowired
  public CarsController(CarsService service) {
    this.service = service;
  }

  // get
  @GetMapping
  public List<Car> getCars() {
    return service.getCars();
  }

  // post
  @PostMapping
  public void addNewCar(@RequestBody Car car) {
    service.addNewCar(car);
  }

}
