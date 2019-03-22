package com.github.glo2003.exceptions;

public class ParameterParsingException extends Exception implements HttpException {

    public ParameterParsingException(String paramName, String paramType) {
        super(String.format("Parameter %s should be of type %s", paramName, paramType));
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
