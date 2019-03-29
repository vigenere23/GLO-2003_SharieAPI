package com.github.glo2003.exceptions;

public class ItemAlreadyExistsException extends Exception implements HttpException {
    private static final long serialVersionUID = 5212064302538629909L;

    public ItemAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() { return 400; }
}
