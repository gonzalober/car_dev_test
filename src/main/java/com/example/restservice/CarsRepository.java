package com.example.restservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {

  @Query("" + "SELECT CASE WHEN COUNT(s) > 0 THEN " + "TRUE ELSE FALSE END " + "FROM Car s " + "WHERE s.make = ?1")
  Boolean selectExistsMake(String make);

  Optional<Car> findById(Long id);

}
