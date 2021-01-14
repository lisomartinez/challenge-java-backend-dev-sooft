package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findByDirectorId(String id);

    void deleteByDirectorId(String id);

}
