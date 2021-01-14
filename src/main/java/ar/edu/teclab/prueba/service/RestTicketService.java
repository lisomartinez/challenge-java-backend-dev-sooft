package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.Comment;
import ar.edu.teclab.prueba.model.DomainException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestTicketService implements TicketService {
    public static final String TICKET_BASE_URL = "https://teclab1593636133.zendesk.com/api/v2/tickets";
    public static final String TICKET = "ticket";
    public static final String COMMENT = "comment";
    public static final String BODY = "body";
    public static final String AUDIT = "audit";
    public static final String EVENTS = "events";
    public static final int FIRST_EVENT = 0;

    private RestTemplate httpClient;
    private ObjectMapper objectMapper;

    @Autowired
    public RestTicketService(RestTemplateBuilder restTemplateBuilder,
                             @Value("${zendesk.username}") String user,
                             @Value("${zendesk.password}") String password,
                             ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        setupRestTemplate(restTemplateBuilder, user, password);
    }

    private void setupRestTemplate(RestTemplateBuilder restTemplateBuilder, String user, String password) {
        DefaultUriTemplateHandler baseUrl = new DefaultUriTemplateHandler();
        baseUrl.setBaseUrl(TICKET_BASE_URL);
        this.httpClient = restTemplateBuilder
                .basicAuthorization(user, password)
                .uriTemplateHandler(baseUrl)
                .errorHandler(new RestTicketServiceResponseErrorHandler())
                .build();
    }

    @Override
    public List<Comment> getCommentsOfTicket(int ticketId) {
        String url = urlFromTicketId(ticketId);
        ResponseEntity<ObjectNode> response = httpClient.getForEntity("/" + ticketId + "/comments.json", ObjectNode.class);
        return extractCommentsFromResponse(response);
    }

    private List<Comment> extractCommentsFromResponse(ResponseEntity<ObjectNode> response) {
        JsonNode comments = response.getBody().get("comments");
        try {
            return objectMapper.readValue(String.valueOf(comments), new TypeReference<ArrayList<Comment>>() {
            });
        } catch (IOException e) {
            throw new DomainException("Could not get comments");
        }
    }

    private String urlFromTicketId(int ticketId) {
        return TICKET_BASE_URL + ticketId + "/comments.json";
    }

    @Override
    public void commentOnTicket(int ticketId, Comment aComment) {
        Map<String, String> requestBody = new HashMap<>();

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody);
        httpClient.exchange(TICKET_BASE_URL + ticketId, HttpMethod.PUT, request, Void.class);
    }

    @Override
    public String getCommentsOfTicketTest(int id) {
        String url = "/" + id + "/comments.json";
        ResponseEntity<ObjectNode> response = httpClient.getForEntity(url, ObjectNode.class);
        return response.getBody().get("comments").toString();
    }

    @Override
    public Comment addCommentToTicket(int id, Comment comment) {
        String url = "/" + id;
        HttpEntity<ObjectNode> request = createRequestFrom(comment);
        ResponseEntity<ObjectNode> response = httpClient.exchange(url, HttpMethod.PUT, request, ObjectNode.class);
        return parseResponse(comment, response);
    }

    private Comment parseResponse(Comment comment, ResponseEntity<ObjectNode> response) {
        long createdCommentId = getCommentIdFrom(response);
        comment.setId(createdCommentId);
        return comment;
    }

    private long getCommentIdFrom(ResponseEntity<ObjectNode> response) {
        ObjectNode body = response.getBody();
        JsonNode audit = body.get(AUDIT);
        JsonNode events = audit.get(EVENTS);
        JsonNode firstEvent = events.get(FIRST_EVENT);
        return firstEvent.get("id").asLong();
    }

    private HttpEntity<ObjectNode> createRequestFrom(Comment comment) {
        ObjectNode root = objectMapper.createObjectNode();
        ObjectNode commentBodyNode = createBody(comment);
        ObjectNode commentNode = createComment(commentBodyNode);
        root.set(TICKET, commentNode);
        return new HttpEntity<>(root);
    }

    private ObjectNode createComment(ObjectNode commentBodyNode) {
        ObjectNode commentNode = objectMapper.createObjectNode();
        commentNode.set(COMMENT, commentBodyNode);
        return commentNode;
    }

    private ObjectNode createBody(Comment comment) {
        ObjectNode commentBodyNode = objectMapper.createObjectNode();
        commentBodyNode.put(BODY, comment.getBody());
        return commentBodyNode;
    }

}
