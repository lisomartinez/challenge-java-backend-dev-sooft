package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.controller.TicketController;
import ar.edu.teclab.prueba.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DegreeController.class)
@ContextConfiguration(classes = {Application.class})
public class DegreeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DegreeService degreeService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void canCreateADegree() throws Exception {
        CreateDegreeDto createDegreeDto = createDegreeDto();
        Degree expectedResponse = createDegree();
        when(degreeService.create(createDegreeDto)).thenReturn(expectedResponse);

        String content = mapper.writeValueAsString(createDegreeDto);
        System.out.println(content);
        MvcResult response = mvc.perform(post("/degrees").contentType(MediaType.APPLICATION_JSON)
                                                          .content(content))
                                 .andDo(print())
                                 .andExpect(status().isCreated())
                                 .andReturn();
        assertThatCreatedDegreeIsEqualTo(response, expectedResponse, createDegreeDto);
    }

    private void assertThatCreatedDegreeIsEqualTo(MvcResult response, Degree expectedResponse, CreateDegreeDto requestBody)
    throws IOException {
        String body = response.getResponse().getContentAsString();
        Degree degree = mapper.readValue(body, Degree.class);
        assertThat(degree).isEqualToComparingFieldByField(expectedResponse);
        assertThat(degree.getTitle()).isEqualTo(requestBody.getTitle());
        assertThat(degree.getType()).isEqualTo(DegreeType.valueOf(requestBody.getType().toUpperCase(Locale.ROOT)));
        assertThat(degree.getDirector().getDirectorId()).isEqualTo(requestBody.getDirectorId());
        assertThat(degree.getId()).isNull();
        assertThat(degree.getDegreeId()).isNotNull();
    }

    private CreateDegreeDto createDegreeDto() {
        return new CreateDegreeDto("degree title", "online", "725d1d64-0ac7-4d09-99ee-0a9920453fe3");
    }

    private Degree createDegree() {
        Degree degree = Degree.createDegree(
                                    "07268d95-5a0f-4b3a-8a94-bd23926ceee5",
                                    "degree title",
                                   DegreeType.ONLINE,
                                   Director.create("725d1d64-0ac7-4d09-99ee-0a9920453fe3",
                                                   "Juan",
                                                   "Perez",
                                                   "juanperez@gmail.com"));
        return degree;
    }
}
