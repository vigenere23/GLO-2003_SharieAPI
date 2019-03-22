package com.github.glo2003.exceptions;

public class JsonDeserializingException extends Exception implements HttpException {
    public JsonDeserializingException(String className) {
        super(String.format("Could not deserialize json to %s class", className));
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
