package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.DomainException;
import ar.edu.teclab.prueba.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({DomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage errorResponse(HttpServletRequest request, DomainException e) {
        return ErrorMessage.anErrorMessage()
                           .setMessage(e.getMessage())
                           .setPath(request.getRequestURL().toString())
                           .setMethod(request.getMethod())
                           .setStatusCode(400)
                           .build();
    }
}
