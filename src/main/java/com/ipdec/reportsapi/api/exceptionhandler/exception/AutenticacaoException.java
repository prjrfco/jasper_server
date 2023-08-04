package com.ipdec.reportsapi.api.exceptionhandler.exception;

import java.io.Serial;

public class AutenticacaoException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AutenticacaoException(String message) {
        super(message);
    }
}
