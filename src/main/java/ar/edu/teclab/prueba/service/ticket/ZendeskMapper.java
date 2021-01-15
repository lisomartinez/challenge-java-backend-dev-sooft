package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.CommentDto;
import ar.edu.teclab.prueba.model.exceptions.DomainException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZendeskMapper {
    public static final String COMMENT = "comment";
    public static final String BODY = "body";
    public static final String AUDIT = "audit";
    public static final String EVENTS = "events";
    public static final int FIRST_EVENT = 0;

    private ObjectMapper objectMapper;

    @Autowired
    public ZendeskMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    HttpEntity<ObjectNode> createRequestFrom(CommentDto comment) {
        ObjectNode root = objectMapper.createObjectNode();
        ObjectNode commentBodyNode = createBody(comment);
        ObjectNode commentNode = createComment(commentBodyNode);
        root.set(RestTicketService.TICKET, commentNode);
        return new HttpEntity<>(root);
    }

    ObjectNode createBody(CommentDto comment) {
        ObjectNode commentBodyNode = objectMapper.createObjectNode();
        commentBodyNode.put(BODY, comment.getBody());
        return commentBodyNode;
    }

    ObjectNode createComment(ObjectNode commentBodyNode) {
        ObjectNode commentNode = objectMapper.createObjectNode();
        commentNode.set(COMMENT, commentBodyNode);
        return commentNode;
    }

    List<CommentDto> extractCommentsFromResponse(ResponseEntity<ObjectNode> response) {
        JsonNode comments = response.getBody().get("comments");
        try {
            return objectMapper.readValue(String.valueOf(comments), new TypeReference<ArrayList<CommentDto>>() {
            });
        } catch (IOException e) {
            throw new DomainException("Could not get comments");
        }
    }

    public CommentDto parseResponse(CommentDto comment, ResponseEntity<ObjectNode> response) {
        long createdCommentId = getCommentIdFrom(response);
        comment.setId(createdCommentId);
        return comment;
    }

    long getCommentIdFrom(ResponseEntity<ObjectNode> response) {
        ObjectNode body = response.getBody();
        JsonNode audit = body.get(AUDIT);
        JsonNode events = audit.get(EVENTS);
        JsonNode firstEvent = events.get(FIRST_EVENT);
        return firstEvent.get("id").asLong();
    }
}
