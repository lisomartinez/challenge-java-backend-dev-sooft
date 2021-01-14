package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.DirectorDomainException;
import ar.edu.teclab.prueba.model.DirectorNotFoundException;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import ar.edu.teclab.prueba.service.DirectorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class DirectorServiceTest {
    private TestObjectFactory objectFactory = new TestObjectFactory();

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private TestEntityManager entityManager;

    private DirectorService service;

    @Before
    public void setUp() {
        service = new DirectorService(directorRepository);
    }

    @Test
    public void listIsEmptyIfThereIsNotDirectors() {

        List<Director> Directors = service.findAll();
        assertThat(Directors).isEmpty();
    }

    @Test
    public void canListDirectors() {

        Director director = objectFactory.createDirector();
        Director savedDirector = directorRepository.save(director);
        List<Director> directors =
                service.findAll();
        assertThat(directors).containsExactly(savedDirector);
    }

    @Test
    public void canGetDirectorsById() {

        Director director = objectFactory.createDirector();
        Director savedDirector = directorRepository.save(director);
        assertThat(service.findByDirectorId(savedDirector.getDirectorId())).isEqualTo(savedDirector);
    }

    @Test
    public void cannotGetANotSavedDirector() {
        assertThatThrownBy(() -> service.findByDirectorId(TestObjectFactory.DIRECTOR_ID))
                .isExactlyInstanceOf(DirectorNotFoundException.class)
                .hasMessage("Director not found");
    }

    @Test
    public void canDeleteExistingDirectors() {

        Director director = objectFactory.createDirector();
        Director savedDirector = directorRepository.save(director);

        service.deleteByDirectorId(director.getDirectorId());

        assertThatThrownBy(() -> service.findByDirectorId(director.getDirectorId()))
                .isExactlyInstanceOf(DirectorNotFoundException.class)
                .hasMessage("Director not found");
    }

    @Test
    public void cannotDeleteNonExistingDirectors() {
        assertThatThrownBy(() -> service.deleteByDirectorId(TestObjectFactory.DIRECTOR_ID))
                .isExactlyInstanceOf(DirectorDomainException.class)
                .hasMessage("Cannot delete non existing Director");
    }

    @Test
    public void canUpdateAnExistingDirector() {

        Director Director = objectFactory.createDirector();
        Director savedDirector = directorRepository.save(Director);

        Director directorForUpdate = objectFactory.createDirector();
        directorForUpdate.setDirectorId(savedDirector.getDirectorId());
        directorForUpdate.setLastName("Jose");
        directorForUpdate.setLastName("Peralta");
        directorForUpdate.setEmail("josesito@gmail.com");

        Director updatedDirector = service.update(directorForUpdate);

        assertThat(updatedDirector).isEqualTo(directorForUpdate);
    }


    @Test
    public void cannotUpdateANonExistingDirector() {
        Director directorForUpdate = objectFactory.createDirector();
        directorForUpdate.setDirectorId(TestObjectFactory.DIRECTOR_ID);
        directorForUpdate.setLastName("Jose");
        directorForUpdate.setLastName("Peralta");
        directorForUpdate.setEmail("josesito@gmail.com");
        assertThatThrownBy(() -> service.update(directorForUpdate))
                .isExactlyInstanceOf(DirectorDomainException.class)
                .hasMessage("Cannot update a non-existing Director");
    }

    @Test
    public void canCreateADirector() {
        Director director = objectFactory.createDirector();
        Director savedDirector = service.create(director);
        assertThat(service.findByDirectorId(savedDirector.getDirectorId())).isEqualTo(savedDirector);
    }
}
