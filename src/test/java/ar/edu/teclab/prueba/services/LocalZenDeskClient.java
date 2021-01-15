package ar.edu.teclab.prueba.services;

import ar.edu.teclab.prueba.dto.Comment;
import ar.edu.teclab.prueba.service.ticket.ZendeskClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LocalZenDeskClient implements ZendeskClient {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String response = "{}";
    private String added = "";

    @Override
    public ResponseEntity<ObjectNode> getFrom(String url) {
        ObjectNode responseAsJson = readResponse();
        return new ResponseEntity<ObjectNode>(responseAsJson, null, HttpStatus.OK);
    }

    private ObjectNode readResponse() {
        ObjectNode responseAsJson = objectMapper.createObjectNode();
        try {
            responseAsJson = objectMapper.readValue(response, ObjectNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseAsJson;
    }

    @Override
    public ResponseEntity<ObjectNode> put(String url, Comment comment) {
        ObjectNode responseAsJson = readResponse();
        return new ResponseEntity<ObjectNode>(responseAsJson, null, HttpStatus.OK);
    }

    void setResponse(String response) {
        this.response = response;
    }

}
