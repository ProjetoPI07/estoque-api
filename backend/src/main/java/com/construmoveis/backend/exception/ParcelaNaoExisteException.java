package com.construmoveis.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParcelaNaoExisteException extends RuntimeException {

    public ParcelaNaoExisteException() {
        super("Parcela não encontrada.");
    }
}