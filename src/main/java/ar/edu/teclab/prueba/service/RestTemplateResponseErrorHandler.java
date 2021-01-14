package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.TicketNotFoundDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler
  implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
      throws IOException {

        return (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
      throws IOException {

        if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            throw new RemoteServerException("Could not get comments: Remote server unavailable");
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new TicketNotFoundDomainException();
            } else {
                throw new BadRquestException("could not comment on ticket");
            }
        }
    }
}