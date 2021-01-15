package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DegreeRepository extends JpaRepository<Degree, Long> {

    Optional<Degree> findByDegreeId(String degreeId);

    int removeByDegreeId(String degreeId);
}
