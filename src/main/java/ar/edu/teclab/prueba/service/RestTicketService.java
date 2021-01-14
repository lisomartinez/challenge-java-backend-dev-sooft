package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.Comment;
import ar.edu.teclab.prueba.DomainException;
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

    private String username;

    private String password;
    private RestTemplate httpClient;
    private ObjectMapper objectMapper;

    @Autowired
    public RestTicketService(RestTemplateBuilder restTemplateBuilder,
                             @Value("${zendesk.username}") String user,
                             @Value("${zendesk.password}") String password,
                             ObjectMapper objectMapper
    ) {
        this.username = user;
        this.password = password;
        this.objectMapper = objectMapper;

        setupRestTemplate(restTemplateBuilder);
    }

    private void setupRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        DefaultUriTemplateHandler baseUrl = new DefaultUriTemplateHandler();
        baseUrl.setBaseUrl(TICKET_BASE_URL);
        this.httpClient = restTemplateBuilder
                .basicAuthorization(username, password)
                .uriTemplateHandler(baseUrl)
                .errorHandler(new RestTemplateResponseErrorHandler())
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
        ResponseEntity<ObjectNode> response = httpClient.getForEntity(urlFromTicketId(id), ObjectNode.class);
        return response.getBody().get("comments").toString();
    }

}
