package com.construmoveis.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CobrancaPossuiParcelasException extends RuntimeException {

    public CobrancaPossuiParcelasException() {
        super("Não é possível excluir uma cobrança que possui parcelas.");
    }
}