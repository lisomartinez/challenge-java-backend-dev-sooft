package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.dto.DirectorDto;
import ar.edu.teclab.prueba.model.Director;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DegreeSystemTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private TestObjectFactory objectFactory = new TestObjectFactory();

    private String directorId;

    @Before
    public void setUp() throws Exception {
        Director director = Director.create("Juan", "Perez", "juanperez@gmail.com");
        HttpEntity<Director> request = new HttpEntity<>(director);
        ResponseEntity<DirectorDto> response = restTemplate.exchange("http://localhost:" + port + "/degrees/directors",
                                                                     HttpMethod.POST,
                                                                     request,
                                                                     DirectorDto.class);
        this.directorId = response.getBody().getDirectorId();
    }

    @Test
    public void canCreateDegree() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        createDegreeDto.setDirectorId(directorId);
        DegreeDto degreeDto = objectFactory.createDegreeDto();
        degreeDto.setDirector(DirectorDto.from(Director.identifiedAs(directorId)));
        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> exchange =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertResponseIsCorrect(degreeDto, exchange);
    }

    private void assertResponseIsCorrect(DegreeDto degreeDto, ResponseEntity<DegreeDto> exchange) {
        degreeDto.getDirector().setFirstName(null);
        degreeDto.getDirector().setLastName(null);
        degreeDto.getDirector().setEmail(null);

        DegreeDto responseBody = exchange.getBody();
        assertThat(responseBody.getDegreeId()).isNotBlank();
        assertThat(responseBody.getType()).isEqualTo(degreeDto.getType());
        assertThat(responseBody.getDirector().getDirectorId()).isEqualTo(degreeDto.getDirector().getDirectorId());
    }

    @Test
    public void canDeleteDegree() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        createDegreeDto.setDirectorId(directorId);
        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> savedDegreeResponse =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);

        String degreeId = savedDegreeResponse.getBody().getDegreeId();

        ResponseEntity<Void> response =
                this.restTemplate.exchange(url() + "/degrees/" + degreeId, HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void canUpdateDegree() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        createDegreeDto.setDirectorId(directorId);

        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> savedDegreeResponse =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);

        DegreeDto savedDegreeDto = savedDegreeResponse.getBody();
        String degreeId = savedDegreeDto.getDegreeId();

        savedDegreeDto.setTitle("a new Title updated");
        HttpEntity<DegreeDto> updateRequest = new HttpEntity<>(savedDegreeDto);
        ResponseEntity<DegreeDto> exchange =
                this.restTemplate.exchange(url() + "/degrees/" + degreeId,
                                           HttpMethod.PUT,
                                           updateRequest,
                                           DegreeDto.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertResponseIsCorrect(savedDegreeDto, exchange);
    }

    @Test
    public void canGetAllDegrees() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        createDegreeDto.setDirectorId(directorId);

        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> savedDegreeResponse =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);

        DegreeDto savedDegreeDto = savedDegreeResponse.getBody();
        savedDegreeDto.setDirector(DirectorDto.from(Director.identifiedAs(directorId)));
        ResponseEntity<DegreeDto[]> response = this.restTemplate.getForEntity(url() + "/degrees", DegreeDto[].class);
        List<DegreeDto> listOfDegrees = Arrays.asList(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listOfDegrees).contains(savedDegreeDto);
    }

    @Test
    public void canGetDegreeById() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        createDegreeDto.setDirectorId(directorId);

        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> savedDegreeResponse =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);

        DegreeDto savedDegreeDto = savedDegreeResponse.getBody();
        savedDegreeDto.setDirector(DirectorDto.from(Director.identifiedAs(directorId)));

        ResponseEntity<DegreeDto> response =
                this.restTemplate.getForEntity(url() + "/degrees/" + savedDegreeDto.getDegreeId(), DegreeDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedDegreeDto);
    }

    @Test
    public void cannotGetDegreeByIdNonExistent() {
        ResponseEntity<DegreeDto> response =
                this.restTemplate.getForEntity(url() + "/degrees/565c2056-71e0-465e-8c41-7d222153f8f4", DegreeDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void cannotUpdateDegreeNonExistent() {
        DegreeDto degreeDto = objectFactory.createDegreeDto();
        degreeDto.setDirector(DirectorDto.from(Director.identifiedAs(directorId)));

        HttpEntity<DegreeDto> updateRequest = new HttpEntity<>(degreeDto);
        ResponseEntity<DegreeDto> response =
                this.restTemplate.exchange(url() + "/degrees/" + degreeDto.getDegreeId(),
                                           HttpMethod.PUT,
                                           updateRequest,
                                           DegreeDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void cannotDeleteDegreeByIdNonExistent() {
        ResponseEntity<DegreeDto> response =
                restTemplate.exchange(url() + "/degrees/565c2056-71e0-465e-8c41-7d222153f8f4",
                                      HttpMethod.DELETE,
                                      null,
                                      DegreeDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private String url() {
        return "http://localhost:" + port;
    }
}
