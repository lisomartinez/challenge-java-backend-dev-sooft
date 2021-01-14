package ar.edu.teclab.prueba;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
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

    public void deleteByDegreeId(String directorId) {
        degreeRepository.removeByDirectorId(directorId);
    }

    public Degree update(Degree degreeForUpdate) {
        return degreeRepository.update(degreeForUpdate);
    }

    public Degree create(CreateDegreeDto degreeDto) {
        Degree degree = Degree.createDegree(degreeDto.getTitle(),
                                            DegreeType.valueOf(degreeDto.getType().toUpperCase(Locale.ROOT)),
                                            Director.identifiedAs(degreeDto.getDirectorId()));
        return degreeRepository.save(degree);
    }
}
