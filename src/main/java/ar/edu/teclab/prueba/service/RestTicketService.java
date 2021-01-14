package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.Comment;
import ar.edu.teclab.prueba.DomainException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestTicketService implements TicketService {
    public static final String TICKET_BASE_URL = "https://teclab1593636133.zendesk.com/api/v2/tickets/";
    private RestTemplateBuilder restTemplateBuilder;
    private ObjectMapper objectMapper;

    @Autowired
    public RestTicketService(RestTemplateBuilder restTemplateBuilder,
                             ObjectMapper objectMapper
    ) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Comment> getCommentsOfTicket(int ticketId) {
        RestTemplate restTemplate = createRestTemplateWithBasicAuth();

        ResponseEntity<ObjectNode> response = restTemplate.getForEntity(urlFromTicketId(ticketId), ObjectNode.class);

        assertCommentsOfTicketResponseIsValid(response);
        return extractCommentsFromResponse(response);
    }

    private void assertCommentsOfTicketResponseIsValid(ResponseEntity<ObjectNode> response) {
        HttpStatus statusCode = response.getStatusCode();

        if (statusCode.is2xxSuccessful()) return;

        if (statusCode.equals(HttpStatus.NOT_EXTENDED))
            throw new DomainException("Cannot get comments of non-existent tickets");

        if (statusCode.is4xxClientError()) throw new DomainException("Could not get comments");

        if (statusCode.is5xxServerError())
            throw new DomainException("Could not get comments: Remote server unavailable");
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

    private RestTemplate createRestTemplateWithBasicAuth() {
        return restTemplateBuilder.basicAuthorization("jorge.danni@teclab.edu.ar", "Abril2019")
                                  .build();
    }

    @Override
    public void commentOnTicket(int ticketId, Comment aComment) {
        RestTemplate restTemplate = createRestTemplateWithBasicAuth();
        Map<String, String> requestBody = new HashMap<>();

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody);
        ResponseEntity<Void> response =
                restTemplate.exchange(TICKET_BASE_URL + ticketId, HttpMethod.PUT, request, Void.class);

        assertCommentOnTicketResponseIsValid(response);
    }

    private void assertCommentOnTicketResponseIsValid(ResponseEntity<Void> response) {
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode.is2xxSuccessful()) return;
        if (statusCode.equals(HttpStatus.NOT_FOUND)) throw new DomainException("Cannot comment on non-existent ticket");
        if (statusCode.is4xxClientError()) throw new DomainException("could not comment on ticket");
        if (statusCode.is5xxServerError())
            throw new DomainException("Could not get comments: Remote server unavailable");
    }
}
