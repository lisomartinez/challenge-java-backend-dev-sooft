package ar.edu.teclab.prueba.controller.exceptions;

import ar.edu.teclab.prueba.model.exceptions.*;
import ar.edu.teclab.prueba.shared.BadRquestException;
import ar.edu.teclab.prueba.shared.TicketNotFoundDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            DomainException.class,
            DegreeDomainException.class,
            TicketNotFoundDomainException.class,
            DirectorDomainException.class})
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

    @ExceptionHandler({DegreeNotFoundException.class, DirectorNotFoundException.class})
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

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage internalError(HttpServletRequest request, Exception e) {
        return ErrorMessage.anErrorMessage()
                           .setMessage("Server could not process request")
                           .setPath(request.getRequestURL().toString())
                           .setMethod(request.getMethod())
                           .setStatusCode(500)
                           .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
