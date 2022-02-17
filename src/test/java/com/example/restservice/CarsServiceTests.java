package com.example.restservice;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CarsController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class CarsServiceTests {

  @Mock
  private CarsRepository carsRepository;

  @MockBean
  private CarsController carsController;

  @InjectMocks
  private CarsService underTest;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    underTest = new CarsService(carsRepository);
    this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();

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
  void cantDeleteCarBecauseDoesNotExist() throws Exception {
    // given

    mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/api/menu/cars/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
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
  void cantAddNewCarMissingAttributes() throws Exception {
    // given

    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/menu/cars")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void testUpdateCar() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/menu/cars")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
