package ar.edu.teclab.prueba;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DegreeServiceTest {

    private DegreeRepository degreeRepository;

    @Before
    public void setUp() throws Exception {
        degreeRepository = new InMemoryDegreeRepository();
    }

    @Test
    public void listIsEmptyIfThereIsNotDegrees() {
        DegreeService service = new DegreeService(degreeRepository);
        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).isEmpty();
    }

    @Test
    public void canListDegrees() {
        DegreeService service = new DegreeService(degreeRepository);
        Degree degree = createDegree();
        Degree savedDegree = degreeRepository.save(degree);
        List<Degree> degrees =
                service.findAll();
        assertThat(degrees).containsExactly(savedDegree);
    }

    private Degree createDegree() {
        return Degree.createDegree("degree title",
                                   DegreeType.ONLINE,
                                   Director.create("725d1d64-0ac7-4d09-99ee-0a9920453fe3",
                                                   "Juan",
                                                   "Perez",
                                                   "juanperez@gmail.com"));
    }

    @Test
    public void canGetDegreesById() {
        DegreeService service = new DegreeService(degreeRepository);
        Degree degree = createDegree();
        Degree savedDegree = degreeRepository.save(degree);
        assertThat(service.findById(savedDegree.getDirectorId())).isEqualTo(savedDegree);
    }

    @Test
    public void cannotGetANotSavedDegree() {
        DegreeService service = new DegreeService(degreeRepository);
        String directorId = "d4f4a37a-0b70-4c3a-8815-48542d556528";
        assertThatThrownBy(() -> service.findById(directorId))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void canDeleteExistingDegrees() {
        DegreeService service = new DegreeService(degreeRepository);
        Degree degree = createDegree();
        Degree savedDegree = degreeRepository.save(degree);

        service.deleteByDirectorId(degree.getDirectorId());

        assertThatThrownBy(() -> service.findById(degree.getDirectorId()))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Degree not found");
    }

    @Test
    public void cannotDeleteNonExistingDegrees() {
        DegreeService service = new DegreeService(degreeRepository);
        String directorId = "d4f4a37a-0b70-4c3a-8815-48542d556528";

        assertThatThrownBy(() -> service.deleteByDirectorId(directorId))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot delete non existing Degree");
    }

    @Test
    public void canUpdateAnExistingDegree() {
        DegreeService service = new DegreeService(degreeRepository);
        Degree degree = createDegree();
        Degree savedDegree = degreeRepository.save(degree);
        Degree degreeForUpdate = createDegree();
        degreeForUpdate.setDirectorId(savedDegree.getDirectorId());
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);

        Degree updatedDegree = service.update(degreeForUpdate);
        assertThat(updatedDegree).isEqualTo(degreeForUpdate);
    }

    @Test
    public void cannotUpdateANonExistingDegree() {
        String directorId = "d4f4a37a-0b70-4c3a-8815-48542d556528";
        DegreeService service = new DegreeService(degreeRepository);
        Degree degreeForUpdate = createDegree();
        degreeForUpdate.setDirectorId(directorId);
        degreeForUpdate.setTitle("New Title");
        degreeForUpdate.setType(DegreeType.ON_SITE);
        assertThatThrownBy(() -> service.update(degreeForUpdate))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot update a non existing Degree");
    }
}
