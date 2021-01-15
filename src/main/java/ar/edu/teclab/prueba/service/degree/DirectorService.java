package ar.edu.teclab.prueba.service.degree;

import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DirectorDomainException;
import ar.edu.teclab.prueba.model.exceptions.DirectorNotFoundException;
import ar.edu.teclab.prueba.repository.DirectorRepository;
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
            assertIsNotDirectingAnDegree(id);
            int deleted = directorRepository.deleteByDirectorId(id);
            assertWasPresent(deleted);
        } catch (IllegalArgumentException ex) {
            throw new DirectorDomainException("Cannot delete non existing Director");
        }
    }

    private void assertWasPresent(int deleted) {
        if (deleted <= 0) {
            throw new DirectorDomainException("Cannot delete non existing Director");
        }
    }

    private void assertIsNotDirectingAnDegree(String id) {
        int directing = directorRepository.getNumberOfDegreeDirectedBy(id);
        if (directing > 0) {
            throw new DirectorInDegreeException("Cannot delete a Director who is directing a degree");
        }
    }
}

