package com.github.glo2003.exceptions;

public class JsonSerializingException extends Exception implements HttpException {
    private static final long serialVersionUID = 7138545856972071185L;

    public JsonSerializingException(String className) {
        super(String.format("Could not serialize %s class to json", className));
    }

    @Override
    public int getHttpStatus() {
        return 500;
    }
}
