package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.controller.TicketController;
import ar.edu.teclab.prueba.controller.exceptions.ErrorMessage;
import ar.edu.teclab.prueba.dto.TicketNotFoundDomainException;
import ar.edu.teclab.prueba.service.TicketService;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void canGetCommentsOfTicket() throws Exception {
        when(ticketService.getCommentsOfTicket(9)).thenReturn(commentList());

        String ticketId = "9";
        MvcResult mvcResult = this.mvc.perform(get("/tickets/{id}/comments", ticketId)
                                                       .accept(MediaType.APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();

        assertThatCommentsWasReturned(mvcResult);
    }

    private void assertThatNoCommentWasReturned(MvcResult mvcResult) throws IOException {
        List<Comment> comments = jsonToCommentList(mvcResult);
        assertThat(comments).isEmpty();
    }

    private List<Comment> commentList() {
        return Arrays.asList(
                new Comment(1L, "first comment"),
                new Comment(2L, "second comment"),
                new Comment(3L, "third comment")
        );
    }

    @Test
    public void canGetEmptyListOfCommentsOfTicket() throws Exception {
        when(ticketService.getCommentsOfTicket(9)).thenReturn(emptyCommentList());

        String ticketId = "10";
        MvcResult mvcResult = this.mvc.perform(get("/tickets/{id}/comments", ticketId)
                                                       .accept(MediaType.APPLICATION_JSON))
                                      .andExpect(status().isOk())
                                      .andReturn();

        assertThatNoCommentWasReturned(mvcResult);
    }

    @Test
    public void cannotGetCommentOfNonExistingTicket() throws Exception {
        when(ticketService.getCommentsOfTicket(12)).thenThrow(new TicketNotFoundDomainException());

        ErrorMessage expectedError = ErrorMessage.anErrorMessage()
                                                 .setMessage("Cannot get comments of non-existent tickets")
                                                 .setPath("http://localhost/tickets/12/comments")
                                                 .setMethod("GET")
                                                 .setStatusCode(400)
                                                 .build();

        String ticketId = "12";
        MvcResult mvcResult = this.mvc.perform(get("/tickets/{id}/comments", ticketId)
                                                       .accept(MediaType.APPLICATION_JSON))
                                      .andExpect(status().isBadRequest())
                                      .andReturn();

        assertThatBadRequestWasReturnedWithMessage(mvcResult, expectedError);
    }

    private void assertThatBadRequestWasReturnedWithMessage(MvcResult mvcResult, ErrorMessage expected)
    throws IOException {
        ErrorMessage errorMessage = mapper.readValue(extractResponseFrom(mvcResult), ErrorMessage.class);
        assertThat(errorMessage).isEqualToComparingFieldByField(expected);
    }


    private List<Comment> emptyCommentList() {
        return Collections.emptyList();
    }

    private void assertThatCommentsWasReturned(MvcResult mvcResult) throws java.io.IOException {
        List<Comment> comments = jsonToCommentList(mvcResult);
        assertThat(comments).containsExactlyElementsOf(commentList());
    }

    private List<Comment> jsonToCommentList(MvcResult mvcResult) throws java.io.IOException {
        return mapper.readValue(extractResponseFrom(mvcResult), new TypeReference<ArrayList<Comment>>() {});
    }

    private String extractResponseFrom(MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }
}
