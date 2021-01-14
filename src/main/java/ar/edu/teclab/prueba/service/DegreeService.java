package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.*;
import ar.edu.teclab.prueba.model.exceptions.DegreeDomainException;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.repository.DegreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class DegreeService {
    private final DegreeRepository degreeRepository;
    private DirectorService directorService;

    @Autowired
    public DegreeService(DegreeRepository degreeRepository,
                         DirectorService directorService
    ) {
        this.degreeRepository = degreeRepository;
        this.directorService = directorService;
    }

    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }

    public Degree findById(String degreeId) {
        return degreeRepository.findById(degreeId).orElseThrow(DegreeNotFoundException::new);
    }

    public void deleteByDegreeId(String degreeId) {
        try {
            degreeRepository.deleteById(degreeId);
        } catch (IllegalArgumentException ex) {
            throw new DegreeDomainException("Cannot delete non existing Degree");
        }

    }

    public Degree update(Degree degreeForUpdate) {
        assertDirectorExists(degreeForUpdate.getDirector().getDirectorId());
        try {
            return degreeRepository.update(degreeForUpdate);
        } catch (IllegalArgumentException ex) {
            throw new DegreeDomainException("Cannot update a non existing Degree");
        }
    }

    public Degree create(CreateDegreeDto degreeDto) {
        assertDirectorExists(degreeDto.getDirectorId());
        Degree degree = Degree.aDegree()
                             .setTitle(degreeDto.getTitle())
                             .setType(DegreeType.valueOf(degreeDto.getType().toUpperCase(Locale.ROOT)))
                             .setDirector(Director.identifiedAs(degreeDto.getDirectorId()))
                             .build();
        return degreeRepository.save(degree);
    }

    private void assertDirectorExists(String directorId) {
        try {
            directorService.findByDirectorId(directorId);
        } catch (DirectorNotFoundException ex) {
            throw new DegreeDomainException("cannot use a non existent director");
        }
    }
}
