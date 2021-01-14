package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.model.DirectorDomainException;
import ar.edu.teclab.prueba.model.DirectorNotFoundException;
import ar.edu.teclab.prueba.model.exceptions.DegreeDomainException;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import ar.edu.teclab.prueba.model.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
    private DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director findByDirectorId(String id) {
        return directorRepository.findById(id)
                                 .orElseThrow(DirectorNotFoundException::new);
    }

    public Director update(Director director) {
        try {
            return directorRepository.update(director);
        } catch (IllegalArgumentException ex) {
            throw new DirectorDomainException("cannot update non existent director");
        }
    }



    public Director create(Director director) {
        return directorRepository.save(director);
    }

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public void deleteByDirectorId(String id) {
        try {
            directorRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new DirectorDomainException("cannot delete non existent director");
        }
    }
}
