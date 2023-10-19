package fr.arrestier.todomvc.presentation;

import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.InvalidData;
import fr.arrestier.todomvc.domain.exception.NotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { AlreadyExisting.class })
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { NotFound.class })
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { InvalidData.class })
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
