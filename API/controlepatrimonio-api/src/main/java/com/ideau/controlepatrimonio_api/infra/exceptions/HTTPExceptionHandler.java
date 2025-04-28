package com.ideau.controlepatrimonio_api.infra.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HTTPExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HTTPException.class)
    public ResponseEntity<String> httpExceptionHandler(HTTPException exception) {
        return ResponseEntity
            .status(exception.getStatusCode())
            .body(exception.getMessage());    
    }
}
