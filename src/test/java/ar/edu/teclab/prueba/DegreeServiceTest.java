package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DegreeDomainException;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.repository.DegreeRepository;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import ar.edu.teclab.prueba.service.DegreeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class DegreeServiceTest {
    private TestObjectFactory objectFactory = new TestObjectFactory();

    DegreeService service;
    @Autowired
    private DegreeRepository degreeRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DirectorRepository directorRepository;
    private Director director;

    @Before
    public void setUp() {
        Long id = entityManager.persistAndGetId(Director.identifiedAs(TestObjectFactory.DIRECTOR_ID), Long.class);
        director = entityManager.find(Director.class, id);
        entityManager.flush();
        service = new DegreeService(degreeRepository, directorRepository);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void listIsEmptyIfThereIsNotDegrees() {

        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).isEmpty();
    }

    @Test
    public void canListDegrees() {

        Degree degree = objectFactory.createDegree(director);
        Degree savedDegree = degreeRepository.save(degree);
        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).containsExactly(savedDegree);
    }

    @Test
    public void canGetDegreesById() {

        Degree degree = objectFactory.createDegree(director);
        Degree savedDegree = degreeRepository.save(degree);
        assertThat(service.findById(savedDegree.getDegreeId())).isEqualTo(savedDegree);
    }

    @Test
    public void cannotGetANotSavedDegree() {


        assertThatThrownBy(() -> service.findById(TestObjectFactory.DEGREE_ID))
                .isExactlyInstanceOf(DegreeNotFoundException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void canDeleteExistingDegrees() {

        Degree degree = objectFactory.createDegree(director);
        Degree savedDegree = degreeRepository.save(degree);

        service.deleteByDegreeId(degree.getDegreeId());

        assertThatThrownBy(() -> service.findById(degree.getDegreeId()))
                .isExactlyInstanceOf(DegreeNotFoundException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void cannotDeleteNonExistingDegrees() {


        assertThatThrownBy(() -> service.deleteByDegreeId(TestObjectFactory.OTHER_DEGREE_ID))
                .isExactlyInstanceOf(DegreeDomainException.class)
                .hasMessage("Cannot delete non existing Degree");
    }

    @Test
    public void canUpdateAnExistingDegree() {

        Degree degree = objectFactory.createDegree(director);
        Degree savedDegree = degreeRepository.save(degree);
        Degree degreeForUpdate = objectFactory.createDegree();
        degreeForUpdate.setDegreeId(savedDegree.getDegreeId());
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);
        Degree updatedDegree = service.update(degreeForUpdate);
        assertThat(updatedDegree).isEqualTo(degreeForUpdate);
    }


    @Test
    public void cannotUpdateANonExistingDegree() {


        Degree degreeForUpdate = objectFactory.createDegree();
        degreeForUpdate.setDegreeId(TestObjectFactory.DEGREE_ID);
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);
        assertThatThrownBy(() -> service.update(degreeForUpdate))
                .isExactlyInstanceOf(DegreeDomainException.class)
                .hasMessage("Cannot update a non existing Degree");
    }

    @Test
    public void canCreateADegree() {

        CreateDegreeDto degreeDto = new CreateDegreeDto("a title", "online", TestObjectFactory.DIRECTOR_ID, new ArrayList<>());
        Degree savedDegree = service.create(degreeDto);
        assertThat(service.findById(savedDegree.getDegreeId())).isEqualTo(savedDegree);
    }


}
