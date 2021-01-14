package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.*;
import ar.edu.teclab.prueba.repository.DegreeRepository;
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

    public Degree findById(String degreeId) {
        return degreeRepository.findByDirectorId(degreeId).orElseThrow(DegreeNotFoundException::new);
    }

    public void deleteByDegreeId(String degreeId) {
        try {
            degreeRepository.removeByDirectorId(degreeId);
        } catch (IllegalArgumentException ex) {
            throw new DegreeDomainException("Cannot delete non existing Degree");
        }

    }

    public Degree update(Degree degreeForUpdate) {
        try {
            return degreeRepository.update(degreeForUpdate);
        } catch (IllegalArgumentException ex) {
            throw new DegreeDomainException("Cannot update a non existing Degree");
        }
    }

    public Degree create(CreateDegreeDto degreeDto) {
        Degree degree = Degree.createDegree(degreeDto.getTitle(),
                                            DegreeType.valueOf(degreeDto.getType().toUpperCase(Locale.ROOT)),
                                            Director.identifiedAs(degreeDto.getDirectorId()));
        return degreeRepository.save(degree);
    }
}
