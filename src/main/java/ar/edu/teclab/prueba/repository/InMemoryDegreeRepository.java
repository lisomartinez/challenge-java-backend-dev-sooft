package ar.edu.teclab.prueba.repository;

import ar.edu.teclab.prueba.model.Degree;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryDegreeRepository implements DegreeRepository {
    private Map<Long, Degree> degrees;
    private Long lastId = 0L;

    public InMemoryDegreeRepository() {
        this.degrees = new HashMap<>();
    }

    @Override
    public Degree save(Degree degree) {
        lastId++;
        degree.setId(lastId);
        degree.setDegreeId(UUID.randomUUID().toString());
        degrees.put(lastId, degree);
        return degree;
    }

    @Override
    public List<Degree> findAll() {
        return new ArrayList<>(degrees.values());
    }

    @Override
    public Optional<Degree> findById(String degreeId) {
        return degrees.values().stream().filter(degree -> degree.getDegreeId().equals(degreeId)).findFirst();
    }

    @Override
    public void deleteById(String degreeId) {
        OptionalLong asLong = degrees.entrySet()
                                     .stream()
                                     .filter(degree -> degree.getValue().getDegreeId().equals(degreeId))
                                     .mapToLong(Map.Entry::getKey)
                                     .findFirst();

        if (asLong.isPresent())  degrees.remove(asLong.getAsLong());
        else throw new IllegalArgumentException("Cannot delete non existing Degree");
    }

    @Override
    public Degree update(Degree degreeForUpdate) {
        Degree forUpdate = findById(degreeForUpdate.getDegreeId()).orElseThrow(() -> new IllegalArgumentException("Cannot update a non existing Degree"));
        forUpdate.setTitle(degreeForUpdate.getTitle());
        forUpdate.setType(degreeForUpdate.getType());
        return forUpdate;
    }
}
