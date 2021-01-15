package ar.edu.teclab.prueba.services;


import ar.edu.teclab.prueba.dto.Comment;
import ar.edu.teclab.prueba.service.ticket.RestTicketService;
import ar.edu.teclab.prueba.service.ticket.TicketService;
import ar.edu.teclab.prueba.service.ticket.ZendeskMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoteTicketServiceTest {
    public static final int TICKET_ID = 9;
    public static final String COMMENTS_JSON = "comments.json";
    public static final String CREATED_JSON = "created.json";
    TicketService ticketService;
    private LocalZenDeskClient client;

    @Before
    public void setUp() throws Exception {
        client = new LocalZenDeskClient();
        ticketService = new RestTicketService(new ZendeskMapper(new ObjectMapper()), client);
    }

    @Test
    public void returnsEmptyListIfThereIsNoComments() {
        client.setResponse("{\"comments\": [ ]}");
        List<Comment> commentsOfTicket = ticketService.getCommentsOfTicket(TICKET_ID);
        assertThat(commentsOfTicket).isEmpty();
    }

    @Test
    public void returnsCommentsOfTicket() throws URISyntaxException, IOException {
        String response = readComments(COMMENTS_JSON);
        client.setResponse(response);
        List<Comment> commentsOfTicket = ticketService.getCommentsOfTicket(TICKET_ID);
        assertThat(commentsOfTicket).isNotEmpty();
        assertThat(commentsOfTicket).contains(new Comment(1159431333391L, "Prueba de Joseline Limada"));
    }

    private String readComments(String file) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                                        .getResource(file).toURI());
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        return Files.lines(path).collect(Collectors.joining("\n"));
    }

    @Test
    public void canAddCommentsToTickets() throws Exception {
        String response = readComments(CREATED_JSON);
        client.setResponse(response);
        Comment comment = new Comment();
        comment.setBody("Este es un comentario");
        Comment added = ticketService.addCommentToTicket(TICKET_ID, comment);
        assertThat(added).isEqualTo(comment);
    }

}
