package com.example.restservice;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.exception.ApiRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTests {

  @Mock
  private CarsRepository carsRepository;

  @InjectMocks
  private CarsService underTest;

  @BeforeEach
  void setUp() {
    underTest = new CarsService(carsRepository);
  }

  @Test
  public void canDeleteCar() {
    // given
    final Car car = new Car(1L, "ford", "F100", "yellow", 1986);
    final Car car2 = null;

    // when
    underTest.addNewCar(car);

    when(carsRepository.findById(anyLong())).thenReturn(Optional.of(car)).thenReturn(null);

    carsRepository.findById(1L);

    carsRepository.deleteById(car.getId());

    assertEquals(car2, carsRepository.findById(1L));
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
  void canAddNewCarMissingAttributes() {
    // given
    String make = null;
    Car car = new Car(1L, make, "F100", "yellow", 1986);
    Exception exception = assertThrows(ApiRequestException.class, () -> {
      underTest.addNewCar(car);
    });

    String expectedMessage = "bad request. Mandatory car attributes are missing";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));

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
  void testUpdateCar() {
    Long id = 1L;
    Car car = new Car(id, "ford", "F100", "yellow", 1986);
    Car car2 = new Car(id, "GM", "Avalanche", "yellow", 1986);
    underTest.addNewCar(car);
    when(carsRepository.findById(anyLong())).thenReturn(Optional.of(car)).thenReturn(null);
    underTest.updateCar(1L, "Avalanche", "GM");
    assertThat(car).usingRecursiveComparison().isEqualTo(car2);
  }
}
