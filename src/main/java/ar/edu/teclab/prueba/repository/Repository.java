package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {
    Optional<T> findById(String id);

    T update(T entity);

    T save(T entity);

    List<T> findAll();

    void deleteById(String id);
}
