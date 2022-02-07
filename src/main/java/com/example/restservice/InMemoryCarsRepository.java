package com.example.restservice;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCarsRepository implements CrudRepository<Car, Long> {

  @Override
  public <S extends Car> S save(S entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <S extends Car> Iterable<S> saveAll(Iterable<S> entities) {
    // TODO Auto-generated method stub

    Iterable<S> result = new ArrayList<S>();
    for (S entity : entities) {
      ((ArrayList<S>) result).add(save(entity));
    }
    System.out.println("MEMORY---->1" + result);
    return result;
  }

  @Override
  public Optional<Car> findById(Long id) {
    return Optional.of(new Car(1L, "make", "model", "colour", 2L));
  }

  @Override
  public boolean existsById(Long id) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Iterable<Car> findAll() {
    System.out.println("MEMORY");

    return null;
  }

  @Override
  public Iterable<Car> findAllById(Iterable<Long> ids) {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void deleteById(Long id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(Car entity) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> ids) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteAll(Iterable<? extends Car> entities) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub

  }

}
