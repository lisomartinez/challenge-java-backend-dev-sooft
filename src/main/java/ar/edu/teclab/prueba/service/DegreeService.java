package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.*;
import ar.edu.teclab.prueba.repository.DegreeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
            throw new DegreeNotFoundException("Cannot delete non existing Degree");
        }

    }

    public Degree update(Degree degreeForUpdate) {
        try {
            return degreeRepository.update(degreeForUpdate);
        } catch (IllegalArgumentException ex) {
            throw new DegreeNotFoundException("Cannot update a non existing Degree");
        }
    }

    public Degree create(CreateDegreeDto degreeDto) {
        Degree degree = Degree.aDegree()
                             .setTitle(degreeDto.getTitle())
                             .setType(DegreeType.valueOf(degreeDto.getType().toUpperCase(Locale.ROOT)))
                             .setDirector(Director.identifiedAs(degreeDto.getDirectorId()))
                             .build();
        return degreeRepository.save(degree);
    }
}
