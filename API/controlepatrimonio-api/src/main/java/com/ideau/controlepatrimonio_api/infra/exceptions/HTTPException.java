package com.ideau.controlepatrimonio_api.infra.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HTTPException extends RuntimeException {
    
    private final HttpStatus statusCode;
    
    public HTTPException(HttpStatus statusCode, String mensagem) {
        super(mensagem);
        this.statusCode = statusCode;
    }
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
