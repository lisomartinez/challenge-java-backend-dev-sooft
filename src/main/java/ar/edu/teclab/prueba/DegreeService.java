package ar.edu.teclab.prueba;

import java.util.ArrayList;
import java.util.List;

public class DegreeService {
    private final DegreeRepository degreeRepository;

    public DegreeService(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }

    public Degree findById(String directorId) {
        return degreeRepository.findByDirectorId(directorId).orElseThrow(() -> new DomainException("Degree not found"));
    }

    public void deleteByDirectorId(String directorId) {
        degreeRepository.removeByDirectorId(directorId);
    }

    public Degree update(Degree degreeForUpdate) {
        return degreeRepository.update(degreeForUpdate);
    }
}
