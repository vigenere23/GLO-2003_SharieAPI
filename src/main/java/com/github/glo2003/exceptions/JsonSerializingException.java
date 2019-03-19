package com.github.glo2003.exceptions;

public class JsonSerializingException extends Exception implements HttpException {
    public JsonSerializingException(String className) {
        super(String.format("Could not serialize %s class to json", className));
    }

    @Override
    public int getHttpStatus() {
        return 500;
    }
}
