package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.CommentDto;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

@Component
public class RestZendeskClient implements ZendeskClient {
    private RestTemplate httpClient;
    private ZendeskMapper mapper;

    @Autowired
    public RestZendeskClient(RestTemplateBuilder restTemplateBuilder,
                             ZendeskMapper mapper,
                             @Value("${zendesk.base-url}") String url,
                             @Value("${zendesk.username}") String user,
                             @Value("${zendesk.password}") String password
    ) {
        DefaultUriTemplateHandler baseUrl = new DefaultUriTemplateHandler();
        baseUrl.setBaseUrl(url);
        this.httpClient = restTemplateBuilder
                .basicAuthorization(user, password)
                .uriTemplateHandler(baseUrl)
                .errorHandler(new RestTicketServiceResponseErrorHandler())
                .build();
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ObjectNode> getFrom(String url) {
        return httpClient.getForEntity(url, ObjectNode.class);
    }


    @Override
    public ResponseEntity<ObjectNode> put(String url, CommentDto comment) {
        HttpEntity<ObjectNode> request = mapper.createRequestFrom(comment);
        return httpClient.exchange(url, HttpMethod.PUT, request, ObjectNode.class);
    }
}
