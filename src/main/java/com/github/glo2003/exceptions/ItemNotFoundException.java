package com.github.glo2003.exceptions;

public class ItemNotFoundException extends Exception implements HttpException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() {
        return 404;
    }
}
