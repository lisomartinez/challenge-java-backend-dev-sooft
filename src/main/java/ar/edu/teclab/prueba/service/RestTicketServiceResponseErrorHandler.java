package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.dto.TicketNotFoundDomainException;
import ar.edu.teclab.prueba.shared.BadRquestException;
import ar.edu.teclab.prueba.shared.RemoteServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTicketServiceResponseErrorHandler
  implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
      throws IOException {
        return (isClientError(httpResponse) || isServerError(httpResponse));
    }

    private boolean isServerError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR;
    }

    private boolean isClientError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
      throws IOException {

        if (isServerError(httpResponse))
            throw new RemoteServerException("Could not get comments: Remote server unavailable");

        if (isClientError(httpResponse)) {
            if (isNotFound(httpResponse)) {
                throw new TicketNotFoundDomainException();
            } else {
                throw new BadRquestException("could not comment on ticket");
            }
        }
    }

    private boolean isNotFound(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}