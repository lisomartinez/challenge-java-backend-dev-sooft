package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Degree;

import java.util.List;
import java.util.Optional;

public interface DegreeRepository {
    Degree save(Degree degree);

    List<Degree> findAll();

    Optional<Degree> findByDirectorId(String degreeId);

    void removeByDirectorId(String degreeId);

    Degree update(Degree degreeForUpdate);
}
