package com.example.restservice;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CarsServiceTests {

  @Mock
  private CarsRepository carsRepository;

  private CarsService underTest;
  private AutoCloseable autoCloseable;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    underTest = new CarsService(carsRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void canGetCars() {
    // when
    underTest.getCars();
    // then
    verify(carsRepository).findAll();
  }

  @Test
  @Disabled
  void testAddNewCar() {

  }

  @Test
  @Disabled
  void testDeleteCar() {

  }

  @Test
  @Disabled
  void testUpdateCar() {

  }
}
