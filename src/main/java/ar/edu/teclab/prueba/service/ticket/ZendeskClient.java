package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.Comment;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

public interface ZendeskClient {
    ResponseEntity<ObjectNode> getFrom(String url);

    ResponseEntity<ObjectNode> put(String url, Comment comment);
}
