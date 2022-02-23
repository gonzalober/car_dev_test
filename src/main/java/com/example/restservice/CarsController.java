package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/cars")
public class CarsController {

  private final CarsService carService;

  @Autowired
  public CarsController(CarsService carService) {
    this.carService = carService;
  }

  public ArrayList<String> getWords(String keyword) {
    String url = "https://api.datamuse.com/words?sl=" + keyword;

    RestTemplate restTemplate = new RestTemplate();

    Word[] wordList = restTemplate.getForObject(url, Word[].class);
    // System.out.println("====////////////////////////////////======" +
    // wordList[2].word);
    ArrayList<String> wordArrayList = new ArrayList<String>();
    for (Word item : wordList) {
      // System.out.println(item.word);
      wordArrayList.add(item.word);
    }
    // System.out.println("-----" + wordArrayList);
    return wordArrayList;
  }

  // get
  @GetMapping
  public List<Car> getCars() {

    // System.out.println(getWords("focus"));
    // System.out.println(carService.getCars());
    List<Car> carsss = carService.getCars();
    // carsss.iterator();
    ArrayList<String> modelWordArrayList = new ArrayList<String>();
    for (Car item : carsss) {
      System.out.println(carsss);
      System.out.println(item.getModel());
      modelWordArrayList.addAll(getWords(item.getModel()));
    }
    System.out.println(modelWordArrayList);
    System.out.println(modelWordArrayList.size());
    return carService.getCars();
  }

  // getById
  @GetMapping("{carId}")
  @ResponseBody
  public Optional<Car> getCarById(@PathVariable("carId") Long carId) {
    Optional<Car> car = carService.getCarById(carId);
    if (car.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with Id '" + carId + "' not found");
    }
    return carService.getCarById(carId);

  }

  // post
  @PostMapping
  public void addNewCar(@RequestBody Car car) {
    String makeCar = car.getMake();
    String modelCar = car.getModel();
    String colourCar = car.getColour();
    Integer yearCar = car.getYear();
    if (makeCar == null || modelCar == null || colourCar == null || yearCar == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad request. Mandatory car attributes are missing");
    }
    carService.addNewCar(car);
  }

  // delete
  @DeleteMapping("{carId}")
  public void deleteCar(@PathVariable("carId") Long carId) throws ResponseStatusException {
    Optional<Car> exists = carService.getCarById(carId);
    if (exists.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with Id '" + carId + "' not found");
    }

    carService.deleteCar(carId);
  }

  // put
  @PutMapping
  public void updateCar(@RequestBody Car car) {
    Optional<Car> exists = carService.getCarById(car.getId());
    if (exists.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car with id " + car.getId() + " does not exist");
    }
    carService.updateCar(car);
  }

}
