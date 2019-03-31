package com.github.glo2003.exceptions;

public class ItemNotFoundException extends Exception implements HttpException {
    private static final long serialVersionUID = -5921148938507454480L;

    public ItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() {
        return 404;
    }
}
