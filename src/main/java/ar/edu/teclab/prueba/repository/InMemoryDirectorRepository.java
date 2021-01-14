package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Director;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDirectorRepository implements DirectorRepository {

    private Map<Long, Director> directors;
    private Long lastId = 0L;

    public InMemoryDirectorRepository() {
        this.directors = new HashMap<>();
    }

    @Override
    public Director save(Director director) {
        lastId++;
        director.setId(lastId);
        director.setDirectorId(UUID.randomUUID().toString());
        directors.put(lastId, director);
        return director;
    }

    @Override
    public List<Director> findAll() {
        return new ArrayList<>(directors.values());
    }

    @Override
    public Optional<Director> findById(String directorId) {
        return directors.values().stream().filter(director -> director.getDirectorId().equals(directorId)).findFirst();
    }

    @Override
    public void deleteById(String directorId) {
        OptionalLong asLong = directors.entrySet()
                                       .stream()
                                       .filter(director -> director.getValue().getDirectorId().equals(directorId))
                                       .mapToLong(Map.Entry::getKey)
                                       .findFirst();

        if (asLong.isPresent()) directors.remove(asLong.getAsLong());
        else throw new IllegalArgumentException("Cannot delete non existing Director");
    }

    @Override
    public Director update(Director directorForUpdate) {
        Director forUpdate = findById(directorForUpdate.getDirectorId()).orElseThrow(() -> new IllegalArgumentException(
                "Cannot update a non existing Director"));
        forUpdate.setEmail(directorForUpdate.getEmail());
        forUpdate.setFirstName(directorForUpdate.getFirstName());
        forUpdate.setLastName(directorForUpdate.getLastName());
        return forUpdate;
    }

    public Director getLast() {
        return directors.entrySet()
                        .stream()
                        .sorted()
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElseThrow(IllegalArgumentException::new);
    }
}
