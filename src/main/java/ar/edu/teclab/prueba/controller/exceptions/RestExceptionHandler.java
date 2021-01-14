package ar.edu.teclab.prueba.controller.exceptions;

import ar.edu.teclab.prueba.dto.TicketNotFoundDomainException;
import ar.edu.teclab.prueba.model.DegreeDomainException;
import ar.edu.teclab.prueba.model.DegreeNotFoundException;
import ar.edu.teclab.prueba.DomainException;
import ar.edu.teclab.prueba.ErrorMessage;
import ar.edu.teclab.prueba.service.BadRquestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({DomainException.class, DegreeDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage domainError(HttpServletRequest request, DomainException e) {
        return ErrorMessage.anErrorMessage()
                           .setMessage(e.getMessage())
                           .setPath(request.getRequestURL().toString())
                           .setMethod(request.getMethod())
                           .setStatusCode(400)
                           .build();
    }

    @ExceptionHandler({DegreeNotFoundException.class, TicketNotFoundDomainException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage degreeNotFound(HttpServletRequest request, DomainException e) {
        return ErrorMessage.anErrorMessage()
                           .setMessage(e.getMessage())
                           .setPath(request.getRequestURL().toString())
                           .setMethod(request.getMethod())
                           .setStatusCode(404)
                           .build();
    }

    @ExceptionHandler({BadRquestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage badRequestException(HttpServletRequest request, Exception e) {
        return ErrorMessage.anErrorMessage()
                           .setMessage(e.getMessage())
                           .setPath(request.getRequestURL().toString())
                           .setMethod(request.getMethod())
                           .setStatusCode(400)
                           .build();
    }

//    @ExceptionHandler({RuntimeException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ErrorMessage internalError(HttpServletRequest request, Exception e) {
//        return ErrorMessage.anErrorMessage()
//                           .setMessage("Server could not process request")
//                           .setPath(request.getRequestURL().toString())
//                           .setMethod(request.getMethod())
//                           .setStatusCode(500)
//                           .build();
//    }


}
