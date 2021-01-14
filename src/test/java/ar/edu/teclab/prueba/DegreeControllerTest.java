package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.controller.DegreeController;
import ar.edu.teclab.prueba.dto.CreateDegreeDto;
import ar.edu.teclab.prueba.dto.DegreeDto;
import ar.edu.teclab.prueba.model.Degree;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.service.DegreeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DegreeController.class)
public class DegreeControllerTest {
    public static final String DIRECTOR_ID = "725d1d64-0ac7-4d09-99ee-0a9920453fe3";
    public static final String DEGREE_ID = "d5dac498-7ad7-4bf7-b94b-c3b283311242";
    private TestObjectFactory objectFactory = new TestObjectFactory();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DegreeService degreeService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void canCreateADegree() throws Exception {
        CreateDegreeDto createDegreeDto = objectFactory.createCreateDegreeDto();
        DegreeDto expectedResponse = objectFactory.createDegreeDto();

        when(degreeService.create(createDegreeDto)).thenReturn(objectFactory.createDegree());

        String content = mapper.writeValueAsString(createDegreeDto);
        MvcResult response = mvc.perform(post("/degrees").contentType(MediaType.APPLICATION_JSON)
                                                         .content(content))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andReturn();
        assertThatCreatedDegreeIsEqualTo(response, expectedResponse, createDegreeDto);
    }


    @Test
    public void canDeleteADegree() throws Exception {
        String degreeId = DEGREE_ID;
        doNothing().when(degreeService).deleteByDegreeId(degreeId);

        mvc.perform(delete("/degrees/{degreeId}", degreeId)
                            .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isNoContent())
           .andReturn();
    }

    @Test
    public void cannotDeleteANonExistentDegree() throws Exception {

        doThrow(DegreeNotFoundException.class).when(degreeService).deleteByDegreeId(TestObjectFactory.DEGREE_ID);

        mvc.perform(delete("/degrees/{degreeId}", TestObjectFactory.DEGREE_ID))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andReturn();
    }

    @Test
    public void cannotUpdateANonExistentDegree() throws Exception {

        Degree degree = objectFactory.createDegree();
        degree.setDegreeId(DEGREE_ID);
        doThrow(DegreeNotFoundException.class).when(degreeService).update(degree);

        String content = mapper.writeValueAsString(DegreeDto.from(degree));
        mvc.perform(put("/degrees/{degreeId}", TestObjectFactory.DEGREE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andReturn();
    }

    @Test
    public void canListAll() throws Exception {

        when(degreeService.findAll()).thenReturn(Arrays.asList(objectFactory.createDegree()));

        mvc.perform(get("/degrees").contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andReturn();
    }

    @Test
    public void canUpdateADegree() throws Exception {
        DegreeDto expectedResponse = objectFactory.createDegreeDto();

        Degree degree = objectFactory.createDegree();
        degree.setDegreeId(DEGREE_ID);

        when(degreeService.update(degree)).thenReturn(degree);

        String content = mapper.writeValueAsString(DegreeDto.from(degree));
        MvcResult response = mvc.perform(put("/degrees/{degreeId}", DEGREE_ID)
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .content(content))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

        assertThatUpdatedDegreeIsEqualTo(response, expectedResponse);
    }

    @Test
    public void canGetARequestById() throws Exception {
        DegreeDto expectedResponse = objectFactory.createDegreeDto();

        when(degreeService.findById(TestObjectFactory.DEGREE_ID)).thenReturn(objectFactory.createDegree());

        MvcResult response =
                mvc.perform(get("/degrees/{id}", TestObjectFactory.DEGREE_ID).contentType(MediaType.APPLICATION_JSON))
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andReturn();
        assertThatCreatedDegreeIsEqualTo(response, expectedResponse);

    }

    private void assertThatCreatedDegreeIsEqualTo(MvcResult response, DegreeDto expectedResponse) throws IOException {
        DegreeDto degreeDto = parseResponse(response);
        assertThat(degreeDto).isEqualToComparingFieldByField(degreeDto);
    }


    private void assertThatUpdatedDegreeIsEqualTo(MvcResult response, DegreeDto requestBody) throws IOException {
        DegreeDto degreeDto = parseResponse(response);
        assertThat(degreeDto).isEqualToComparingFieldByField(degreeDto);
        assertThat(degreeDto.getTitle()).isEqualTo(requestBody.getTitle());
        assertThat(degreeDto.getType()).isEqualTo(requestBody.getType());
        assertThat(degreeDto.getDirector()).isEqualToComparingFieldByField(requestBody.getDirector());
        assertThat(degreeDto.getDegreeId()).isNotNull();
    }

    private void assertThatCreatedDegreeIsEqualTo(MvcResult response,
                                                  DegreeDto expectedResponse,
                                                  CreateDegreeDto requestBody
    )
    throws IOException {
        DegreeDto degreeDto = parseResponse(response);
        assertThat(degreeDto).isEqualToComparingFieldByField(expectedResponse);
        assertThat(degreeDto.getTitle()).isEqualTo(requestBody.getTitle());
        assertThat(degreeDto.getType()).isEqualTo(requestBody.getType());
        assertThat(degreeDto.getDirector().getDirectorId()).isEqualTo(requestBody.getDirectorId());
        assertThat(degreeDto.getDegreeId()).isNotNull();
    }

    private DegreeDto parseResponse(MvcResult response) throws IOException {
        String body = response.getResponse().getContentAsString();
        return mapper.readValue(body, DegreeDto.class);
    }


}
