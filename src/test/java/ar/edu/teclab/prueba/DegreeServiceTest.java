package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DegreeDomainException;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.model.DegreeType;
import ar.edu.teclab.prueba.repository.DegreeRepository;
import ar.edu.teclab.prueba.repository.DirectorRepository;
import ar.edu.teclab.prueba.repository.InMemoryDegreeRepository;
import ar.edu.teclab.prueba.repository.InMemoryDirectorRepository;
import ar.edu.teclab.prueba.service.DegreeService;
import ar.edu.teclab.prueba.service.DirectorService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DegreeServiceTest {
    private TestObjectFactory objectFactory = new TestObjectFactory();
    private DegreeRepository degreeRepository;
    private DirectorService directorService;
    private InMemoryDirectorRepository directorRepository;

    @Before
    public void setUp() throws Exception {
        degreeRepository = new InMemoryDegreeRepository();
        directorRepository = new InMemoryDirectorRepository();
        directorRepository.save(Director.identifiedAs(TestObjectFactory.DIRECTOR_ID));
        directorService = new DirectorService(directorRepository);
    }

    @Test
    public void listIsEmptyIfThereIsNotDegrees() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).isEmpty();
    }

    @Test
    public void canListDegrees() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        Degree degree = objectFactory.createDegree();
        Degree savedDegree = degreeRepository.save(degree);
        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).containsExactly(savedDegree);
    }

    @Test
    public void canGetDegreesById() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        Degree degree = objectFactory.createDegree();
        Degree savedDegree = degreeRepository.save(degree);
        assertThat(service.findById(savedDegree.getDegreeId())).isEqualTo(savedDegree);
    }

    @Test
    public void cannotGetANotSavedDegree() {
        DegreeService service = new DegreeService(degreeRepository, directorService);

        assertThatThrownBy(() -> service.findById(TestObjectFactory.DEGREE_ID))
                .isExactlyInstanceOf(DegreeNotFoundException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void canDeleteExistingDegrees() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        Degree degree = objectFactory.createDegree();
        Degree savedDegree = degreeRepository.save(degree);

        service.deleteByDegreeId(degree.getDegreeId());

        assertThatThrownBy(() -> service.findById(degree.getDegreeId()))
                .isExactlyInstanceOf(DegreeNotFoundException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void cannotDeleteNonExistingDegrees() {
        DegreeService service = new DegreeService(degreeRepository, directorService);

        assertThatThrownBy(() -> service.deleteByDegreeId(TestObjectFactory.DEGREE_ID))
                .isExactlyInstanceOf(DegreeDomainException.class)
                .hasMessage("Cannot delete non existing Degree");
    }

    @Test
    public void canUpdateAnExistingDegree() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        Degree degree = objectFactory.createDegree();
        setDirectorId(degree);
        Degree savedDegree = degreeRepository.save(degree);
        Degree degreeForUpdate = objectFactory.createDegree();
        degreeForUpdate.setDegreeId(savedDegree.getDegreeId());
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);
        setDirectorId(degreeForUpdate);
        Degree updatedDegree = service.update(degreeForUpdate);
        assertThat(updatedDegree).isEqualTo(degreeForUpdate);
    }

    private void setDirectorId(Degree savedDegree) {
        savedDegree.setDirector(directorRepository.getLast());
    }

    @Test
    public void cannotUpdateANonExistingDegree() {

        DegreeService service = new DegreeService(degreeRepository, directorService);
        Degree degreeForUpdate = objectFactory.createDegree();
        degreeForUpdate.setDegreeId(TestObjectFactory.DEGREE_ID);
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);
        setDirectorId(degreeForUpdate);
        assertThatThrownBy(() -> service.update(degreeForUpdate))
                .isExactlyInstanceOf(DegreeDomainException.class)
                .hasMessage("Cannot update a non existing Degree");
    }

    @Test
    public void canCreateADegree() {
        DegreeService service = new DegreeService(degreeRepository, directorService);
        CreateDegreeDto degreeDto = new CreateDegreeDto("a title", "online", TestObjectFactory.DIRECTOR_ID, new ArrayList<>());
        setDirectorId(degreeDto);
        Degree savedDegree = service.create(degreeDto);
        assertThat(service.findById(savedDegree.getDegreeId())).isEqualTo(savedDegree);
    }

    private void setDirectorId(CreateDegreeDto degreeDto) {
        degreeDto.setDirectorId(directorRepository.getLast().getDirectorId());
    }
}
