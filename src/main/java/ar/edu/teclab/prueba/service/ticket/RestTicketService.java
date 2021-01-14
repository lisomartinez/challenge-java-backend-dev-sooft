package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.Comment;
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

import java.util.List;

@Service
public class RestTicketService implements TicketService {
    public static final String TICKET_BASE_URL = "https://teclab1593636133.zendesk.com/api/v2/tickets";
    public static final String TICKET = "ticket";
    public static final String COMMENTS = "/comments.json";
    public static final String SLASH = "/";

    private RestTemplate httpClient;
    private ZendeskMapper zendeskMapper;

    @Autowired
    public RestTicketService(RestTemplateBuilder restTemplateBuilder,
                             @Value("${zendesk.username}") String user,
                             @Value("${zendesk.password}") String password,
                             ZendeskMapper zendeskMapper
    ) {
        this.zendeskMapper = zendeskMapper;
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
        String url = SLASH + ticketId + COMMENTS;
        ResponseEntity<ObjectNode> response = httpClient.getForEntity(url, ObjectNode.class);
        return zendeskMapper.extractCommentsFromResponse(response);
    }

    @Override
    public String getCommentsOfTicketTest(int ticketId) {
        String url = SLASH + ticketId + COMMENTS;
        ResponseEntity<ObjectNode> response = httpClient.getForEntity(url, ObjectNode.class);
        return response.getBody().get("comments").toString();
    }

    @Override
    public Comment addCommentToTicket(int id, Comment comment) {
        String url = SLASH + id;
        HttpEntity<ObjectNode> request = zendeskMapper.createRequestFrom(comment);
        ResponseEntity<ObjectNode> response = httpClient.exchange(url, HttpMethod.PUT, request, ObjectNode.class);
        return zendeskMapper.parseResponse(comment, response);
    }

}
