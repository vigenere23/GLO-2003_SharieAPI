package com.github.glo2003.exceptions;

public class JsonDeserializingException extends Exception implements HttpException {
    private static final long serialVersionUID = -1752303801597680649L;

    public JsonDeserializingException(String className) {
        super(String.format("Could not deserialize json to %s class", className));
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
