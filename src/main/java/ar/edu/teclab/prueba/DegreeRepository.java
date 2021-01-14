package ar.edu.teclab.prueba;

import java.util.List;
import java.util.Optional;

public interface DegreeRepository {
    Degree save(Degree degree);

    List<Degree> findAll();

    Optional<Degree> findByDirectorId(String directorId);

    void removeByDirectorId(String directorId);

    Degree update(Degree degreeForUpdate);
}
