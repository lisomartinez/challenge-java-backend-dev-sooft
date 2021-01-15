package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findByDirectorId(String id);

    int deleteByDirectorId(String id);

    @Query("select count(d) from Degree d where d.director.directorId = :id")
    int getNumberOfDegreeDirectedBy(@Param("id") String id);
}
