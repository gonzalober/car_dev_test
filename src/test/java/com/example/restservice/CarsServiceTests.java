package com.example.restservice;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@WebMvcTest(CarsController.class)
public class CarsServiceTests {

  @MockBean
  private CarsRepository carsRepository;

  @MockBean
  private CarsController carsController;

  @MockBean
  private CarsService underTest;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    underTest = new CarsService(carsRepository);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void canGetCarById() throws Exception {
    // given
    Car car = new Car(1L, "ford", "F100", "yellow", 1986);
    // when
    underTest.addNewCar(car);

    BDDMockito.given(underTest.getCarById(anyLong())).willReturn(Optional.of(car));

    when(underTest.getCarById(anyLong())).thenReturn(Optional.of(car));

    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/menu/cars/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
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
    Car car = new Car(1L, "ford", "F100", "yellow", 1986);
    this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    // when
    when(carsRepository.findById(anyLong())).thenReturn(Optional.of(car)).thenReturn(null);

    mockMvc
        .perform(MockMvcRequestBuilders
            .delete("/api/menu/cars/100")
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
    this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/menu/cars")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void testUpdateCar() throws Exception {
    this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    mockMvc
        .perform(MockMvcRequestBuilders
            .put("/api/menu/cars")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
