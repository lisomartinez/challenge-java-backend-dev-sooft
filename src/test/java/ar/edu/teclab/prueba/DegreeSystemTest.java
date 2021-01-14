package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DegreeSystemTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private TestObjectFactory objectFactory = new TestObjectFactory();

    @Test
    public void canCreateDegree() {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        DegreeDto degreeDto = objectFactory.createDegreeDto();

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
        HttpEntity<CreateDegreeDto> request = new HttpEntity<>(createDegreeDto);
        ResponseEntity<DegreeDto> savedDegreeResponse =
                this.restTemplate.exchange(url() + "/degrees", HttpMethod.POST, request, DegreeDto.class);

        DegreeDto savedDegreeDto = savedDegreeResponse.getBody();
        String degreeId = savedDegreeDto.getDegreeId();

        savedDegreeDto.setTitle("a new Title updated");
        HttpEntity<DegreeDto> updateRequest = new HttpEntity<>(savedDegreeDto);
        ResponseEntity<DegreeDto> exchange =
                this.restTemplate.exchange(url() + "/degrees/" + degreeId, HttpMethod.PUT, updateRequest, DegreeDto.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertResponseIsCorrect(savedDegreeDto, exchange);
    }

    private String url() {
        return "http://localhost:" + port;
    }
}
