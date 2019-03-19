package com.github.glo2003.exceptions;

public class ItemAlreadyExistsException extends Exception implements HttpException {
    public ItemAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() { return 400; }
}
