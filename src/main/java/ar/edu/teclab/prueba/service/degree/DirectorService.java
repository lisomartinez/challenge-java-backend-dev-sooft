package ar.edu.teclab.prueba.service.degree;

import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DirectorDomainException;
import ar.edu.teclab.prueba.model.exceptions.DirectorNotFoundException;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class DirectorService {
    private DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director findByDirectorId(String id) {
        return directorRepository.findByDirectorId(id)
                                 .orElseThrow(DirectorNotFoundException::new);
    }

    @Transactional
    public Director update(Director director) {
        try {
            Director toUpdate = directorRepository.findByDirectorId(director.getDirectorId())
                                                  .orElseThrow(IllegalArgumentException::new);
            toUpdate.setFirstName(director.getFirstName());
            toUpdate.setLastName(director.getLastName());
            toUpdate.setEmail(director.getEmail());
            return toUpdate;
        } catch (IllegalArgumentException ex) {
            throw new DirectorDomainException("Cannot update a non-existing Director");
        }
    }

    public Director create(Director director) {
        director.setDirectorId(UUID.randomUUID().toString());
        return directorRepository.save(director);
    }

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Transactional
    public void deleteByDirectorId(String id) {
        try {
            int deleted = directorRepository.deleteByDirectorId(id);
            if (deleted <= 0) {
                throw new DirectorDomainException("Cannot delete non existing Director");
            }
        } catch (IllegalArgumentException ex) {
            throw new DirectorDomainException("Cannot delete non existing Director");
        } catch (ConstraintViolationException ex) {
            throw new DirectorDomainException("Cannot delete a Director who is member of a Degree");
        }
    }

    private void assertDirectorExists(String id) {
        directorRepository.findByDirectorId(id).orElseThrow(IllegalArgumentException::new);
    }
}
