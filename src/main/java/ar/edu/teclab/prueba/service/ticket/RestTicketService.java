package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.CommentDto;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestTicketService implements TicketService {
    public static final String TICKET = "ticket";
    public static final String COMMENTS = "/comments.json";
    public static final String SLASH = "/";

    private final ZendeskMapper mapper;
    private final ZendeskClient client;

    @Autowired
    public RestTicketService(ZendeskMapper mapper, ZendeskClient client) {
        this.mapper = mapper;
        this.client = client;
    }

    @Override
    public List<CommentDto> getCommentsOfTicket(int ticketId) {
        String url = SLASH + ticketId + COMMENTS;
        ResponseEntity<ObjectNode> response = client.getFrom(url);
        return mapper.extractCommentsFromResponse(response);
    }

    @Override
    public String getCommentsOfTicketTest(int ticketId) {
        String url = SLASH + ticketId + COMMENTS;
        ResponseEntity<ObjectNode> response = client.getFrom(url);
        return response.getBody().get("comments").toString();
    }

    @Override
    public CommentDto addCommentToTicket(int id, CommentDto comment) {
        String url = SLASH + id;
        ResponseEntity<ObjectNode> response = client.put(url, comment);
        return mapper.parseResponse(comment, response);
    }

}
