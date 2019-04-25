package com.github.glo2003.exceptions;

public class InvalidParameterException extends Exception implements HttpException {
    private static final long serialVersionUID = -3569674561655353935L;

    public InvalidParameterException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
