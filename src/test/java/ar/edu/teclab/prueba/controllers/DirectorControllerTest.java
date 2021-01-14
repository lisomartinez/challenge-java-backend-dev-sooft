package ar.edu.teclab.prueba.controllers;

import ar.edu.teclab.prueba.controller.DirectorController;
import ar.edu.teclab.prueba.dto.DirectorDto;
import ar.edu.teclab.prueba.model.Director;
import ar.edu.teclab.prueba.model.exceptions.DegreeNotFoundException;
import ar.edu.teclab.prueba.service.degree.DirectorService;
import ar.edu.teclab.prueba.utils.TestObjectFactory;
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
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DirectorController.class)
public class DirectorControllerTest {
    public static final String DIRECTORS = "/degrees/directors";
    public static final String DIRECTORS_ID = "/degrees/directors/{id}";
    private final TestObjectFactory objectFactory = new TestObjectFactory();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DirectorService directorService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void canCreateADegree() throws Exception {
        Director director = objectFactory.createDirector();

        when(directorService.create(director)).thenReturn(director);

        String content = mapper.writeValueAsString(DirectorDto.from(director));
        MvcResult response = mvc.perform(post(DIRECTORS).contentType(MediaType.APPLICATION_JSON)
                                                        .content(content))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andReturn();
        assertThatAreEqual(response, DirectorDto.from(director));
    }


    @Test
    public void canDeleteADegree() throws Exception {
        doNothing().when(directorService).deleteByDirectorId(TestObjectFactory.DIRECTOR_ID);

        mvc.perform(delete(DIRECTORS_ID, TestObjectFactory.DIRECTOR_ID)
                            .contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isNoContent())
           .andReturn();
    }

    @Test
    public void cannotDeleteANonExistentDegree() throws Exception {

        doThrow(DegreeNotFoundException.class).when(directorService).deleteByDirectorId(TestObjectFactory.DEGREE_ID);

        mvc.perform(delete(DIRECTORS_ID, TestObjectFactory.DEGREE_ID))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andReturn();
    }

    @Test
    public void cannotUpdateANonExistentDegree() throws Exception {

        Director director = objectFactory.createDirector();

        doThrow(DegreeNotFoundException.class).when(directorService).update(director);

        String content = mapper.writeValueAsString(DirectorDto.from(director));
        mvc.perform(put(DIRECTORS, TestObjectFactory.DEGREE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andReturn();
    }

    @Test
    public void canListAll() throws Exception {
        when(directorService.findAll()).thenReturn(Collections.singletonList(objectFactory.createDirector()));

        mvc.perform(get(DIRECTORS).contentType(MediaType.APPLICATION_JSON))
           .andDo(print())
           .andExpect(status().isOk())
           .andReturn();
    }

    @Test
    public void canUpdateADegree() throws Exception {
        Director director = objectFactory.createDirector();

        Director directorToUpdate = objectFactory.createDirector();
        directorToUpdate.setDirectorId(director.getDirectorId());
        directorToUpdate.setLastName("Sanchez");

        when(directorService.update(director)).thenReturn(directorToUpdate);

        String content = mapper.writeValueAsString(DirectorDto.from(director));
        MvcResult response = mvc.perform(put(DIRECTORS)
                                                 .contentType(MediaType.APPLICATION_JSON)
                                                 .content(content))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

        assertThatArDiffersInLastName(response, DirectorDto.from(director));
    }

    private void assertThatArDiffersInLastName(MvcResult response, DirectorDto from) throws IOException {
        DirectorDto directorDto = parseResponse(response);
        assertThat(directorDto.getDirectorId()).isEqualTo(from.getDirectorId());
        assertThat(directorDto.getFirstName()).isEqualTo(from.getFirstName());
        assertThat(directorDto.getLastName()).isNotEqualTo(from.getLastName());
        assertThat(directorDto.getEmail()).isEqualTo(from.getEmail());
    }

    @Test
    public void canGetARequestById() throws Exception {
        Director director = objectFactory.createDirector();

        when(directorService.findByDirectorId(director.getDirectorId())).thenReturn(director);

        MvcResult response =
                mvc.perform(get(DIRECTORS_ID, TestObjectFactory.DIRECTOR_ID).contentType(MediaType.APPLICATION_JSON))
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andReturn();
        assertThatAreEqual(response, DirectorDto.from(director));

    }

    private void assertThatAreEqual(MvcResult response, DirectorDto expectedResponse) throws IOException {
        DirectorDto directorDto = parseResponse(response);
        assertThat(directorDto).isEqualToComparingFieldByField(expectedResponse);
    }


    private DirectorDto parseResponse(MvcResult response) throws IOException {
        String body = response.getResponse().getContentAsString();
        return mapper.readValue(body, DirectorDto.class);
    }

}
