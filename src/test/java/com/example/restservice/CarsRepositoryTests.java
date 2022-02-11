package com.example.restservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest // wired up db and autowired carRepo
public class CarsRepositoryTests {

  @Autowired
  private CarsRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void itShouldCheckWhenCarMakeExists() {

    // given
    String make = "ford";
    Car car = new Car(1L, make, "F100", "yellow", 1986);
    // in memory "database" H2
    underTest.save(car);
    // when
    boolean expected = underTest.selectExistsMake(make);
    // then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckWhenCarMakeDoesNotExists() {

    // given
    String make = "ford";
    // when
    boolean expected = underTest.selectExistsMake(make);
    // then
    assertThat(expected).isFalse();
  }
}
