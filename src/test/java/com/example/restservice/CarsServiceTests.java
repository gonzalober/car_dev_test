package com.example.restservice;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTests {

  @Mock
  private CarsRepository carsRepository;

  private CarsService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CarsService(carsRepository);
  }

  @Test
  void canGetCars() {
    // when
    underTest.getCars();
    // then
    verify(carsRepository).findAll();
  }

  @Test
  void canAddNewCar() {
    // given
    String make = "ford";
    Car car = new Car(1L, make, "F100", "yellow", 1986);
    // when
    underTest.addNewCar(car);
    // then
    ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);

    verify(carsRepository).save(carArgumentCaptor.capture());

    Car capturedCar = carArgumentCaptor.getValue();

    assertThat(capturedCar).isEqualTo(car);
  }

  @Test
  void cantDeleteCarBecauseDoesNotExist() {
    // given
    String make = "ford";
    Car car = new Car(1L, make, "F100", "yellow", 1986);
    assertThatThrownBy(() -> underTest.deleteCar(car.getId()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("car with id " + car.getId() + " does not exist");

  }

  @Test
  void canDeleteCar() {
    // given
    Long id = 1L;
    Car car = new Car(id, "ford", "F100", "yellow", 1986);
    Car car2 = new Car(id, "ford", "F100", "yellow", 1986);

    when(carsRepository.selectExistsId(anyLong())).thenReturn(true);
    // (carsRepository.selectExistsId(car.getId())).willReturn(true);
    underTest.deleteCar(id);

    verify(carsRepository, times(1)).delete(car2);

  }

  @Test
  @Disabled
  void testUpdateCar() {

  }
}
