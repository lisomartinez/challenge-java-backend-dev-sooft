package ar.edu.teclab.prueba;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
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
    public Optional<Degree> findByDirectorId(String directorId) {
        return degrees.values().stream().filter(degree -> degree.getDegreeId().equals(directorId)).findFirst();
    }

    @Override
    public void removeByDirectorId(String directorId) {
        OptionalLong asLong = degrees.entrySet()
                                     .stream()
                                     .filter(degree -> degree.getValue().getDegreeId().equals(directorId))
                                     .mapToLong(Map.Entry::getKey)
                                     .findFirst();
        if (asLong.isPresent())  degrees.remove(asLong.getAsLong());
        else throw new DomainException("Cannot delete non existing Degree");
    }

    @Override
    public Degree update(Degree degreeForUpdate) {
        Degree forUpdate = findByDirectorId(degreeForUpdate.getDegreeId()).orElseThrow(() -> new DomainException("Cannot update a non existing Degree"));
        forUpdate.setTitle(degreeForUpdate.getTitle());
        forUpdate.setType(degreeForUpdate.getType());
        return forUpdate;
    }
}
