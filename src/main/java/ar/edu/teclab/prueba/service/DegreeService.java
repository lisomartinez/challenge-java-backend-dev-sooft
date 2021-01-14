package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DegreeDomainException;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.repository.DegreeRepository;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class DegreeService {
    private final DegreeRepository degreeRepository;
    private DirectorRepository directorRepository;

    @Autowired
    public DegreeService(DegreeRepository degreeRepository,
                         DirectorRepository directorRepository
    ) {
        this.degreeRepository = degreeRepository;

        this.directorRepository = directorRepository;
    }

    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }

    public Degree findById(String degreeId) {
        return degreeRepository.findByDegreeId(degreeId).orElseThrow(DegreeNotFoundException::new);
    }

    public void deleteByDegreeId(String degreeId) {
        try {
            int deleted = degreeRepository.removeByDegreeId(degreeId);
            if (deleted <= 0) throw new DegreeDomainException("Cannot delete non existing Degree");
        } catch (ConstraintViolationException ex) {
            throw new DegreeDomainException("Cannot delete non existing Degree");
        }
    }

    @Transactional
    public Degree update(Degree degreeForUpdate) {
        try {
            Degree degree = getDegree(degreeForUpdate);
            Director director = getDirector(degreeForUpdate);
            updateDegree(degreeForUpdate, degree, director);
            return degreeRepository.save(degree);
        } catch (IllegalArgumentException ex) {
            throw new DegreeDomainException("Cannot update a non existing Degree");
        } catch (Exception ex) {
            throw new DegreeDomainException("error during degree actualization");
        }
    }

    private void updateDegree(Degree degreeForUpdate, Degree degree, Director director) {
        degree.setDirector(director);
        degree.setTitle(degreeForUpdate.getTitle());
        degree.setType(degreeForUpdate.getType());
        degree.setStudyPlant(degreeForUpdate.getStudyPlan());
    }

    private Director getDirector(Degree degreeForUpdate) {
        return directorRepository.findByDirectorId(degreeForUpdate.getDirector().getDirectorId())
                                 .orElseThrow(IllegalArgumentException::new);
    }

    private Degree getDegree(Degree degreeForUpdate) {
        return degreeRepository.findByDegreeId(degreeForUpdate.getDegreeId())
                               .orElseThrow(IllegalArgumentException::new);
    }

    public Degree create(CreateDegreeDto degreeDto) {
        Director director = directorRepository.findByDirectorId(degreeDto.getDirectorId())
                                              .orElseThrow(() -> new DegreeDomainException(
                                                      "Cannot create a Degree with an nonexistent Director"));
        Degree degree = Degree.aDegree()
                              .setDegreeId(UUID.randomUUID().toString())
                              .setTitle(degreeDto.getTitle())
                              .setType(DegreeType.valueOf(degreeDto.getType().toUpperCase(Locale.ROOT)))
                              .setDirector(director)
                              .build();
        return degreeRepository.save(degree);
    }
}
